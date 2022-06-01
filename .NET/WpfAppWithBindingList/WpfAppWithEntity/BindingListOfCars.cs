using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace WpfAppWithEntity
{
    class BindingListOfCars : BindingList<Car>
    {

        private ArrayList selected;
        private PropertyDescriptor sortPropertyValue;
        private ListSortDirection sortDirectionValue;
        private bool isSortedValue = false;

        public BindingListOfCars(List<Car> list)
        {
            foreach (var car in list)
            {
                Add(car);
            }
        }

        protected override void ApplySortCore(PropertyDescriptor prop, ListSortDirection direction)
        {
            var sortedList = new ArrayList();
            var unsortedList = new ArrayList(Count);
            if (prop.PropertyType.GetInterface("IComparable") != null)
            {
                sortPropertyValue = prop;
                sortDirectionValue = direction;

                foreach (Car car in Items)
                {
                    if (!sortedList.Contains(prop.GetValue(car)))
                    {
                        sortedList.Add(prop.GetValue(car));
                    }

                }
                sortedList.Sort();
                if (direction == ListSortDirection.Descending)
                {
                    sortedList.Reverse();
                }

                for (int i = 0; i < sortedList.Count; i++)
                {
                    var findIdx = FindIdx(prop.Name, sortedList[i]);
                    if (findIdx != null)
                    {
                        foreach (var index in findIdx)
                        {
                            unsortedList.Add(Items[index]);
                        }
                    }
                }

                if (unsortedList != null)
                {
                    Clear();
                    foreach (Car elem in unsortedList)
                    {
                        Add(elem);
                    }
                    isSortedValue = true;
                    OnListChanged(new ListChangedEventArgs(ListChangedType.Reset, -1));
                }


            }
        }

        public void Sort(string property, ListSortDirection direction)
        {
            PropertyDescriptorCollection properties = TypeDescriptor.GetProperties(typeof(Car));
            PropertyDescriptor prop = properties.Find(property, true);
            if (prop != null)
            {
                ApplySortCore(prop, direction);
            }
            else
            {
                throw new NotSupportedException($"Cannot sort by {prop.Name}, this property doesn\'t exist.");
            }
        }

        private int FindCore(PropertyDescriptor prop, object key, bool isEngine)
        {
            PropertyInfo propInfo;
            if (isEngine)
            {
                propInfo = typeof(Engine).GetProperty(prop.Name);
            }
            else
            {
                propInfo = typeof(Car).GetProperty(prop.Name);
            }
            selected = new ArrayList();
            int found = -1;

            if (key != null)
            {
                for (int i = 0; i < Count; i++)
                {
                    if (isEngine)
                    {
                        double neverused;
                        if (Double.TryParse(key.ToString(), out neverused))
                        {
                            if (propInfo.GetValue(Items[i].motor, null).Equals(Double.Parse(key.ToString())))
                            {
                                found++;
                                selected.Add(i);
                            }
                        }
                        else
                        {
                            if (propInfo.GetValue(Items[i].motor, null).Equals(key))
                            {
                                found++;
                                selected.Add(i);
                            }
                        }

                    }
                    else
                    {
                        if (propInfo.GetValue(Items[i], null).Equals(key))
                        {
                            found++;
                            selected.Add(i);
                        }
                    }
                }
            }
            return found;
        }

        public int[] FindIdx(string property, object key)
        {
            PropertyDescriptorCollection properties;
            bool isEngine = property.Contains("motor.");
            if (isEngine)
            {

                properties = TypeDescriptor.GetProperties(typeof(Engine));
                property = property.Split('.').Last();
            }
            else
            {
                properties = TypeDescriptor.GetProperties(typeof(Car));
            }
            PropertyDescriptor prop = properties.Find(property, true);
            if (prop != null)
            {
                if (FindCore(prop, key, isEngine) >= 0)
                {
                    return (int[])(selected.ToArray(typeof(int)));
                }
            }
            return null;

        }

        public List<Car> FindCars(string property, object key)
        {
            List<Car> listOfMatchingCars = new List<Car>();
            int[] indices = FindIdx(property, key);
            if (indices != null)
            {
                foreach (var index in FindIdx(property, key))
                {
                    listOfMatchingCars.Add(Items[index]);
                }
                return listOfMatchingCars;
            }
            else return null;
        }

    }
}
