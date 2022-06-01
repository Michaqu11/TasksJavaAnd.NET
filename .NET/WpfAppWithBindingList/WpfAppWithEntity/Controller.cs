using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WpfAppWithEntity
{
    class Controller
    {
        private delegate int CompareCarsPowerDelegate(Car car1, Car car2);

        public static List<Car> myCars = new List<Car>(){
                new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
                new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
                new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
                new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
                new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
                new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
                new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
                new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
                new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
        };
        public static void Zad1()
        {
            Writer.initWriter();

            var zad1a = from car in
                        (from c in myCars
                         where c.model == "A6"
                         select new
                         {
                             model = c.motor.model == "TDI" ? "diesel" : "petrol",
                             hppl = c.motor.horsePower / c.motor.displacement
                         })
                        group car by car.model into grouped
                        select new
                        {
                            m = grouped.First().model.ToString(),
                            avg = grouped.Average(s => s.hppl).ToString()
                        } into selected
                        orderby selected.avg descending
                        select $"{selected.m} = {selected.avg}";
            Writer.Write("Zad1a\n");
            Writer.Write(zad1a);
            Writer.Write("\n");

            var zad1b = myCars
                .Where(c => c.model == "A6")
                .Select(c =>
                    new
                    {
                        model = c.motor.model == "TDI"
                            ? "diesel"
                            : "petrol",
                        hppl = c.motor.horsePower / c.motor.displacement,
                    })
                    .GroupBy(c => c.model)
                    .Select(c => new
                    {
                        m = c.First().model.ToString(),
                        avg = c.Average(s => s.hppl).ToString()
                    })
                    .OrderByDescending(c => c.avg)
                    .Select(c => $"{c.m} = {c.avg}");

            Writer.Write("Zad1b\n");
            Writer.Write(zad1b);
            Writer.Write("\n");

        }
        private static int ComparePower(Car c1, Car c2)
        {
            return c1.motor.horsePower >= c2.motor.horsePower ? 1 : -1;
        }
        private static bool checkModel(Car c)
        {
            return c.motor.model == "TDI";
        }
        private static void ShowMessageBox(Car c)
        {
            string message = c.ToString();
            string caption = "Zad2";
            MessageBoxButtons buttons = MessageBoxButtons.OK;
            DialogResult result = MessageBox.Show(message, caption, buttons);
        }
        public static void Zad2()
        {
            List<Car> myCarsCopy = new List<Car>(myCars);
            CompareCarsPowerDelegate arg1 = ComparePower;
            Predicate<Car> arg2 = checkModel;
            Action<Car> arg3 = ShowMessageBox;

            myCarsCopy.Sort(new Comparison<Car>(arg1));

            Writer.Write("myCars sorted by horsePower:");
            Writer.Write(myCarsCopy);
            myCarsCopy.FindAll(arg2).ForEach(arg3);

        }
        public static void Zad3()
        {
            BindingListOfCars blindListCars = new BindingListOfCars(myCars);
            Writer.Write("\n\n");

            blindListCars.Sort("model", System.ComponentModel.ListSortDirection.Ascending);
            Writer.Write("sort by model");
            Writer.Write(blindListCars);

            Writer.Write("\n");

            var searchResult = blindListCars.FindCars("motor.displacement", 3.0);
            Writer.Write("Search cars with motor 3.0l");
            if (searchResult != null)
            {
                Writer.Write(searchResult);
            }
            else
            {
                Writer.Write("no resul");
            }

            Writer.Write("\n");
        }

}
}
