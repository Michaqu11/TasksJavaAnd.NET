using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;

namespace Lab2WPF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        DirectoryInfo directory;
        public MainWindow()
        {
            InitializeComponent();
        }

        private void Exit(object sender, RoutedEventArgs e)
        {
            System.Windows.Application.Current.Shutdown();
        }

        private void Open(object sender, RoutedEventArgs e)
        {
            FolderBrowserDialog dlg = new System.Windows.Forms.FolderBrowserDialog() { Description = "Select directory to open" };
            var result = dlg.ShowDialog();
            if (result.ToString() != string.Empty)
            {
                directory = new DirectoryInfo(dlg.SelectedPath);
                var root = new TreeViewItem
                {
                    Header = directory.Name,
                    Tag = directory.FullName
                };
                addAttributes(root, true);

                view.Items.Add(root);
                createTree(directory.FullName, root);

            }
            Trace.WriteLine(directory.ToString());
        }
        public TreeViewItem AddElement(TreeViewItem root, DirectoryInfo file)
        {
            var item = new TreeViewItem
            {
                Header = file.Name,
                Tag = file.FullName
            };
            if (file.FullName != directory.FullName)
                root.Items.Add(item);

            return item;
        }
        private void addAttributesToItem(TreeViewItem item, string optionName, Action<object, RoutedEventArgs> methodName)
        {

            var menuItem = new System.Windows.Controls.MenuItem { Header = optionName };
            menuItem.Click += new RoutedEventHandler(methodName);
            item.ContextMenu.Items.Add(menuItem);
            item.Selected += new RoutedEventHandler(attributes);
        }


        public void addAttributes(TreeViewItem item, bool dir)
        {
            item.ContextMenu = new System.Windows.Controls.ContextMenu();
            addAttributesToItem(item, "Delete", deleteItem);
            if (dir)
            {
                addAttributesToItem(item, "Create", createItem);
            }
            else
            {
                addAttributesToItem(item, "Open", openItem);
            }
        }

        private void DeleteDirectory(string path)
        {
            if (Directory.Exists(path))
            {
                var dir = Directory.GetDirectories(path);
                var file = Directory.GetFiles(path);
                List<string> myList = dir.ToList();
                myList.AddRange(file.ToList());

                foreach (string p in myList)
                {
                    DeleteDirectory(p);
                }
                Directory.Delete(path);
            }
            else
            {
                File.SetAttributes(path, FileAttributes.Normal);
                File.Delete(path);
            }
        }
        private void deleteItem(object sender, RoutedEventArgs e)
        {

            TreeViewItem selectedItem = (TreeViewItem)view.SelectedItem;
            string dir = (string)selectedItem.Tag;

            DirectoryInfo p = new DirectoryInfo(dir);

            File.SetAttributes(p.FullName, FileAttributes.Normal);
            if (Directory.Exists(dir))
            {
                DeleteDirectory(p.FullName);
        }
            else
            {
                File.Delete(dir);
            }
            if ((TreeViewItem)view.Items[0] == selectedItem)
                view.Items.Clear();
            else
            {
                var root = (TreeViewItem)selectedItem.Parent;
                root.Items.Remove(selectedItem);
            }
            Trace.WriteLine("Delete");
        }
        private void createItem(object sender, RoutedEventArgs e)
        {
            TreeViewItem selectedItem = (TreeViewItem)view.SelectedItem;
            if (selectedItem != null)
            {
                Dialog dial = new Dialog((string)selectedItem.Tag);
                dial.ShowDialog();

                if (!dial.get_info_created())
                {
                    return;
                }
                if (Directory.Exists(dial.get_path()))
                {
                    var item = this.AddElement(selectedItem, new DirectoryInfo(dial.get_path()));
                    addAttributes(item, true);

                }
                else if (File.Exists(dial.get_path()))
                {
                    var item = this.AddElement(selectedItem, new DirectoryInfo(dial.get_path()));
                    addAttributes(item, false);
                }
            }
        }
        private void openItem(object sender, RoutedEventArgs e)
        {
            TreeViewItem selectedItem = (TreeViewItem)view.SelectedItem;
            string txt = File.ReadAllText((string)selectedItem.Tag);
            contentView.Content = new TextBlock() { Text = txt };
            Trace.WriteLine("Open");
        }
        public void createTree(string path, TreeViewItem root)
        {
            if (Directory.Exists(path))
            {
                var dir = Directory.GetDirectories(path);
                var file = Directory.GetFiles(path);
                List<string> list = dir.ToList();
                list.AddRange(file.ToList());

                var item = AddElement(root, new DirectoryInfo(path));

                addAttributes(item, true);

                foreach (string p in list)
                {
                    if (path == directory.FullName)
                        createTree(p, root);
                    else
                        createTree(p, item);
                }
            }
            else
            {
                var item = AddElement(root, new DirectoryInfo(path));
                addAttributes(item, false);
            }
        }
        private void attributes(object sender, RoutedEventArgs e)
        {
            TreeViewItem selectedItem = (TreeViewItem)view.SelectedItem;
            string rahs="";
            FileAttributes attributes = new FileInfo((string)selectedItem.Tag).Attributes;
            rahs += ((attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly) ? "r" : "-";
            rahs += ((attributes & FileAttributes.Archive) == FileAttributes.Archive) ? "a" : "-";
            rahs += ((attributes & FileAttributes.Hidden) == FileAttributes.Hidden) ? "h" : "-";
            rahs += ((attributes & FileAttributes.System) == FileAttributes.System) ? "s" : "-";

            DOS.Text = rahs;
        }

    }
    


}
