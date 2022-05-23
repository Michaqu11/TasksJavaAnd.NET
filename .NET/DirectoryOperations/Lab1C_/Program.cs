using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_
{
    class Program
    {
        static void Main(string[] args){
            if (args.Length > 0) {
                DirectoryFiles dirOper = new DirectoryFiles();
                dirOper.printAllDirectory(new DirectoryInfo(args[0]));
                Console.WriteLine("\n\n");
                dirOper.findElements(new DirectoryInfo(args[0]), 0);
                dirOper.printElements();
                DateTime youngestTime;
                string youngestName;
                (youngestTime, youngestName) = DirectoryFiles.FindYoungestFile(new DirectoryInfo(args[0]));
                Console.WriteLine("Najmlodszy plik to:" + youngestName + " stworzony: " + youngestTime);
                Console.WriteLine("\n\n");
                Collections col = new Collections();
                col.Collection(new DirectoryInfo(args[0]));
                col.print(col.Deserialize());
                Console.WriteLine("\n\n");
            }
            else
            {
                Console.WriteLine("Brak argumentu\n\n");
            }
            Console.WriteLine("Wcisnij klawisz aby zakonczyc");
            Console.ReadLine();
        }
    }
}
