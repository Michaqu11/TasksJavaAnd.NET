using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Lab3C_
{
    public class Engine
    {
        public double displacement { get; set; }
        public double horsePower { get; set; }

        [XmlAttribute]
        public string model { get; set; }

        public Engine()
        {
            this.displacement = 0.0;
            this.horsePower = 0.0;
            this.model = "";
        }
        public Engine(double displacement, double horsePower, string model)
        {
            this.displacement = displacement;
            this.horsePower = horsePower;
            this.model = model;
        }
    }
}
