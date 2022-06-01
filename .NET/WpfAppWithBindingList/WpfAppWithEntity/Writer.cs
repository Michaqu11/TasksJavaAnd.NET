using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WpfAppWithEntity
{
    class Writer
    {
        private static string file = "../../out.txt";
        public static void initWriter()
        {
            using (FileStream fs = File.Create(file))
            {

            }
        }
        public static void Write(String text)
        {
            using (StreamWriter writer = File.AppendText(file))
            {
                writer.WriteLine(text);
            }
        }
        public static void Write(ArrayList list)
        {
            foreach (var item in list)
            {
                Write(item.ToString());
            }
        }

        public static void Write<T>(IEnumerable<T> ts)
        {
            foreach (var elem in ts)
            {
                Write(elem.ToString());
            }
        }
        public static void Write<T>(List<T> list)
        {
            foreach (var item in list)
            {
                Write(item.ToString());
            }
        }
    }
}
