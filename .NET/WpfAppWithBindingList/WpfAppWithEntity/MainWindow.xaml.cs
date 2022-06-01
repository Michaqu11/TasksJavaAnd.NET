using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Forms;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace WpfAppWithEntity
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private BindingListOfCars myCarsBindingList;
        private BindingSource carBindingSource;
        private Dictionary<string, bool> sortingASC = new Dictionary<string, bool>();
        public MainWindow()
        {
            InitializeComponent();
            Controller.Zad1();
            Controller.Zad2();
            Controller.Zad3();


            InitComboBox();
            InitSorting();
            myCarsBindingList = new BindingListOfCars(Controller.myCars);
            carBindingSource = new BindingSource();
            UpdateGrid();
        }
        private void InitComboBox()
        {
            BindingList<string> list = new BindingList<string>();
            list.Add("model");
            list.Add("year");
            list.Add("motor.displacement");
            list.Add("motor.model");
            list.Add("motor.horsePower");
            comboBox.ItemsSource = list;
            comboBox.SelectedIndex = 0;
        }
        private void InitSorting()
        {
            sortingASC.Clear();
            sortingASC.Add("model", false);
            sortingASC.Add("motor", false);
            sortingASC.Add("year", false);

        }
        private void UpdateGrid()
        {

            carBindingSource.DataSource = myCarsBindingList;
            dataGridView1.ItemsSource = carBindingSource;

        }
        private IEnumerable<DataGridRow> GetDataGridRows(System.Windows.Controls.DataGrid grid)
        {
            var itemsSource = grid.ItemsSource as IEnumerable;
            if (null == itemsSource) yield return null;
            foreach (var item in itemsSource)
            {
                var row = grid.ItemContainerGenerator.ContainerFromItem(item) as DataGridRow;
                if (null != row) yield return row;
            }
        }
        private void ButtonSearch(object sender, RoutedEventArgs e)
        {
            IEnumerable<DataGridRow> rowGrid = GetDataGridRows(dataGridView1);
            foreach (var row in rowGrid)
            {
                row.Background = Brushes.LightBlue;
            }

            foreach (Car item in myCarsBindingList)
            {
                if (!Controller.myCars.Contains(item))
                {
                    Controller.myCars.Add(item);
                }
            }

            //myCarsBindingList = new BindingListOfCars(Controller.myCars);
            List<Car> resultListOfCars;
            Int32 tmp;
            if (!searchTextBox.Text.Equals(""))
            {
                string property = comboBox.SelectedItem.ToString();
                if (Int32.TryParse(searchTextBox.Text, out tmp))
                {
                    resultListOfCars = myCarsBindingList.FindCars(property, tmp);
                }
                else
                {
                    resultListOfCars = myCarsBindingList.FindCars(property, searchTextBox.Text);
                }
                int[] indexes = myCarsBindingList.FindIdx(property, tmp);

                    //myCarsBindingList = new BindingListOfCars(resultListOfCars);

                foreach (var row in rowGrid)
                {
                    if (resultListOfCars.Count > 0)
                    {
                        foreach (var car in resultListOfCars)
                        {
                            if (row.Item == car)
                            {
                                row.Background = Brushes.Purple;
                            }
                        }
                    }
                }
                //UpdateGrid();
            }
        }
        private void ButtonReload(object sender, RoutedEventArgs e)
        {
            myCarsBindingList = new BindingListOfCars(Controller.myCars);
            UpdateGrid();
        }
        private void SortColumn(object sender, RoutedEventArgs e)
        {
            var columnHeader = sender as DataGridColumnHeader;
            string columnName = columnHeader.ToString().Split(' ')[1].ToLower();
            bool isAsc = sortingASC[columnName];
            InitSorting();
            if (isAsc == true)
            {
                myCarsBindingList.Sort(columnName, ListSortDirection.Descending);
            }
            else
            {
                myCarsBindingList.Sort(columnName, ListSortDirection.Ascending);
            }

            UpdateGrid();
        }
        private void ButtonDeleteRow(object sender, RoutedEventArgs e)
        {
            for (var vis = sender as Visual; vis != null; vis = VisualTreeHelper.GetParent(vis) as Visual)
                if (vis is DataGridRow)
                {
                    var row = (DataGridRow)vis;
                    Car car = (Car)row.Item;
                    myCarsBindingList.Remove(car);
                    Controller.myCars.Remove(car);
                    UpdateGrid();
                    break;
                }

        }

    }
}
