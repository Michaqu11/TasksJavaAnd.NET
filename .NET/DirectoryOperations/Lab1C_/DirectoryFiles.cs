using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_
{
    class DirectoryFiles
    {
        string elements;
        public DirectoryFiles()
        {
            this.elements = "";
        }

        public void printAllDirectory(DirectoryInfo dir)
        {
          
            
            foreach(var d in dir.GetDirectories())
            {
                Console.WriteLine("{0}: {1}", "Directory ", d.Name);
            }

            foreach (var f in dir.GetFiles())
            {
                Console.WriteLine("{0}: {1}", "File ", f.Name);
            }

        }

        public void printElements()
        {
            Console.WriteLine(elements);
        }

        public string addAttributes(FileSystemInfo dir)
        {
            string attributes = "";
            attributes += dir.Attributes.HasFlag(FileAttributes.ReadOnly) ? "r" : "-";
            attributes += dir.Attributes.HasFlag(FileAttributes.Archive) ? "a" : "-";
            attributes += dir.Attributes.HasFlag(FileAttributes.Hidden) ? "h" : "-";
            attributes += dir.Attributes.HasFlag(FileAttributes.System) ? "s" : "-";
            return attributes;
        }  
        public string NumberOfFilesInDirectory(DirectoryInfo dir)
        {
            int num = (dir.GetDirectories().Length + dir.GetFiles().Length);
            return " (" + num.ToString() + ") ";
        }
        public string getSizeOfFile(FileInfo file)
        {
            long num = file.Length;
            return num.ToString() + " bajtow";
        }
        public void addToElement(FileSystemInfo dir, int depth)
        {
            for (int i = 0; i < depth; i++)
            {
                this.elements += "\t";
            }
            this.elements += dir.Name;
            this.elements +=" ";
            this.elements += (dir.Attributes & FileAttributes.Directory) == FileAttributes.Directory ? NumberOfFilesInDirectory((DirectoryInfo)dir) : getSizeOfFile((FileInfo)dir);
            this.elements += " ";
            this.elements += addAttributes(dir);
            this.elements += "\n";
        }
        public void findElements(DirectoryInfo dir, int depth)
        {
            addToElement(dir, depth);
            if (dir.GetDirectories().Length != 0 || dir.GetFiles().Length != 0)
            {
                foreach (var f in dir.GetFiles())
                {
                    addToElement(f, depth+1);
                }
                foreach (var d in dir.GetDirectories())
                {
                    findElements(d, depth+1);
                }
            }
        }
        public static Tuple<DateTime, string> FindYoungestFile(DirectoryInfo dir)
        {
            DateTime youngest = dir.CreationTime;
            string youngestName = dir.Name;
            foreach (var f in dir.GetFiles())
            {
                if (f.CreationTime > youngest)
                {
                    youngestName = f.Name;
                    youngest = f.CreationTime;
                }
            }

            foreach (var f in dir.GetDirectories())
            {
                DateTime tempTime;
                string tempFile;
                (tempTime, tempFile) = FindYoungestFile(f);
               
                if (tempTime > youngest)
                {
                    youngestName = tempFile;
                    youngest = tempTime;
                }
            }

            return Tuple.Create(youngest, youngestName);
        }
       
    }
}
