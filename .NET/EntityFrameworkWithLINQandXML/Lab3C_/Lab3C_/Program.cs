using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.XPath;

namespace Lab3C_
{
    class Program
    {


        static void Main(string[] args)
        {
            List<Car> myCars = new List<Car>(){
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

            // Zadanie 1
            var zad1a = from c in myCars
                        where c.model == "A6"
                        select new
                        {
                            engineType = c.motor.model == "TDI" ? "diesel" : "petrol",
                            hppl = c.motor.horsePower / c.motor.displacement
                        };

            var zad1b = from a in zad1a
                        group a.hppl by a.engineType;

            foreach(var i in zad1b) 
            { 
                Console.WriteLine("{0}: {1}", i.Key, i.Average());
            }

            // Zadanie 2
            XmlSerializer serializer = new XmlSerializer(myCars.GetType(), new XmlRootAttribute("cars"));
            using (TextWriter text_writer = new StreamWriter("CarsCollection.xml"))
            {
                serializer.Serialize(text_writer, myCars);
            }

            // Zadanie 3
            XElement rootNode = XElement.Load("CarsCollection.xml");
            double avgHP = (double)rootNode.XPathEvaluate("sum(car/engine[@model != 'TDI']/horsePower) div count(car/engine[@model != 'TDI'])");
            Console.WriteLine("\navg: " + avgHP);
            IEnumerable<XElement> models = rootNode.XPathSelectElements("car[not(model = following::car/model)]/model");
            Console.WriteLine();
            foreach (XElement m in models)
            {
                Console.WriteLine(m);
            }

            // Zadanie 4

            createXmlFromLinq(myCars);

            // Zadanie 5

            XDocument xmlFile = XDocument.Load("template.html");
            var file = xmlFile.Root.LastNode as XElement;
            IEnumerable<XElement> table = from c in myCars
                                          select new XElement("tr",
                                            new XElement("td", c.model),
                                            new XElement("td", c.motor.model),
                                            new XElement("td", c.motor.displacement),
                                            new XElement("td", c.motor.horsePower),
                                            new XElement("td", c.year)
                                          );

            file.Add(new XElement("table", new XAttribute("border", 1), table));
            xmlFile.Save("CarsTable.html");
            Console.WriteLine("\nTabela została utworzona!");

            // Zadanie 6

            XDocument CarsCollection = XDocument.Load("CarsCollection.xml");

            var horsePower = CarsCollection.Descendants("horsePower");    

            foreach (XElement node in horsePower)
            {
                node.Name = "hp";
            }

            var car = CarsCollection.Descendants("car");

            foreach (XElement c in car)
            {
                c.Element("model").Add(new XAttribute("year", c.Element("year").Value));
                c.Element("year").Remove();
            }

            //Console.WriteLine(CarsCollection.ToString());
            CarsCollection.Save("CarsCollection6.xml");
            Console.WriteLine("\nZmodyfikowano CarsCollection.xml!");



            Console.ReadKey();
        }
















        private static void createXmlFromLinq(List<Car> myCars)
        {
            IEnumerable<XElement> nodes = from c in myCars
                                          select new XElement("car",
                                          new XElement("model", c.model),
                                          new XElement("year", c.year),
                                          new XElement("engine",
                                          new XAttribute("model", c.motor.model),
                                          new XElement("displacement", c.motor.displacement),
                                          new XElement("horsePower", c.motor.horsePower)));


            XElement rootNode = new XElement("cars", nodes); //create a root node to contain the query results
            rootNode.Save("CarsFromLinq.xml");
        }
    }
}
