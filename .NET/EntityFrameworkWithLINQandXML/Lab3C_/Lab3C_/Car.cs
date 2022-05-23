using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Lab3C_
{
    [XmlType(TypeName = "car")]
    public class Car
    {
        public string model { get; set; }

        [XmlElement(ElementName = "engine")]
        public Engine motor { get; set; }
        public int year { get; set; }


        public Car()
        {
            this.model = "";
            this.motor = null;
            this.year = 0;
        }
        public Car(string model, Engine motor, int year)
        {
            this.model = model;
            this.motor = motor;
            this.year = year;
        }

    }
}
