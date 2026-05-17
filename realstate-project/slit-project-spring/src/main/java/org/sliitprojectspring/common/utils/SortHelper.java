package org.sliitprojectspring.common.utils;

    import org.springframework.stereotype.Service;
    import org.sliitprojectspring.property.model.Property;

    import java.util.List;


    @Service
    public class SortHelper {







        public   List<Property> sortProperties(  List<Property> properties)
        {

            int n = properties.size();

            for (int i = 1; i < n; i++) {
                Property key = properties.get(i);
                int j = i - 1;


                while (j >= 0 && properties.get(j).getValue() > key.getValue()) {
                    properties.set(j + 1, properties.get(j));
                    j--;
                }
                properties.set(j + 1, key);
            }

            return properties;




        }


    }
