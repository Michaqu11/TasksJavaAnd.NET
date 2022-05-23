using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_
{

    [Serializable]
    internal class Comparer : IComparer<string>
    {
        public int Compare(string x, string y)
        {
            if (x.Length < y.Length) return 1;
            else if (x.Length > y.Length) return -1;
            else return y.CompareTo(x);
        }
    }

    class Collections
    {

        public void Serialize(SortedDictionary<string, int> addresses)
        {
            FileStream fs = new FileStream("DataFile.dat", FileMode.Create);
            BinaryFormatter formatter = new BinaryFormatter();
            try
            {
                formatter.Serialize(fs, addresses);
            }
            catch (SerializationException e)
            {
                Console.WriteLine("Failed: " + e.Message);
                throw;
            }
            finally
            {
                fs.Close();
            }
        }
        public void Collection(DirectoryInfo directoryInfo)
        {
            SortedDictionary<string, int> addresses = new SortedDictionary<string, int>(new Comparer());
            foreach (var file in directoryInfo.GetFiles())
            {
                addresses.Add(file.Name, (int)file.Length);

            }
            foreach (var directory in directoryInfo.GetDirectories())
            {
                addresses.Add(directory.Name, directory.GetFiles().Length + directory.GetDirectories().Length);
            }
            Serialize(addresses);
        }

        public SortedDictionary<string, int> Deserialize()
        {
            SortedDictionary<string, int> addresses = null;
            FileStream fs = new FileStream("DataFile.dat", FileMode.Open);
            try
            {
                BinaryFormatter formatter = new BinaryFormatter();
                addresses = (SortedDictionary<string, int>)formatter.Deserialize(fs);
            }
            catch (SerializationException e)
            {
                Console.WriteLine("Failed: " + e.Message);
                throw;
            }
            finally
            {
                fs.Close();
            }
            return addresses;
        }

        public void print(SortedDictionary<string, int> addresses)
        {
            foreach (var file in addresses)
            {
                Console.WriteLine("{0} -> {1}", file.Key, file.Value);
            }
        }


    }
}
