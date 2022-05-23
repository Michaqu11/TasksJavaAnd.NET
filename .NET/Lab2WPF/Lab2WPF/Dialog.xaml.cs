using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Lab2WPF
{
    /// <summary>
    /// Interaction logic for Dialog.xaml
    /// </summary>
    public partial class Dialog : Window
    {
        string path;
        bool created;

        public Dialog(string path)
        {
            this.path = path;
            this.created = false;
            InitializeComponent();
        }
        private void ok_Click(object sender, RoutedEventArgs e)
        {
            if (!(bool)file.IsChecked && !(bool)directory.IsChecked)
            {
                MessageBox.Show("Wybierz file albo directory!", "Blad", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            else if ((bool)file.IsChecked && !Regex.IsMatch(name.Text, "^[a-zA-Z0-9_~-]{1,8}\\.(txt|php|html)$"))
            {
                MessageBox.Show("Zła nazwa pliku", "Blad", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            else
            {
                string fileName = name.Text;
                path = path + "\\" + fileName;

                FileAttributes attributes = FileAttributes.Normal;
                if ((bool)readOnly.IsChecked) attributes |= FileAttributes.ReadOnly;
                if ((bool)archive.IsChecked) attributes |= FileAttributes.Archive;
                if ((bool)hidden.IsChecked) attributes |= FileAttributes.Hidden;
                if ((bool)systemBtn.IsChecked) attributes |= FileAttributes.System;

                if ((bool)directory.IsChecked) Directory.CreateDirectory(path);
                else File.Create(path);

                File.SetAttributes(path, attributes);
                this.created = true;
                Close();
            }
        }

        public string get_path()
        {
            return path;
        }

        public bool get_info_created()
        {
            return created;
        }
        private void cancel_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }
    }
}
