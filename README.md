Bachelor's degree program **"Computer Science and Technology for Software Production"**

**Development of a java application for the subject "Programming II"**


Making an application that allows employees of a company to sell and rent smartphones. All the smartphones are of the same brand and 3 different types: basic, medium, advanced.
In particular, the medium type is characterized by having, in addition to the basic model, a dual camera, while the advanced type for having, in addition to the basic model, both voice recognition and recognition
by fingerprint.

**Requirements**
- The application allows the basic and advanced smartphones to be purchased and the advanced smartphones to be rented
- Each sale must include the following information:
  - information about the sold smartphones, date of sale, sale price, employee making the sale, etc.
- Each rental will have to report the following information:
  - information on smartphones rented, start date and end date of rental, rental price, employeemaking the rental, etc.
- Each employee will not be allowed to rent more than n smartphones per day (there is no limit to the sale)
- Each customer will not be allowed to rent more than m smartphones per day (there is no limit to purchase)
- Each smartphone, in addition to being defined by its own characterizing attributes, is equipped with its own identification code called IMEI
- Each customer, in addition to being defined by its own characterizing attributes, is uniquely identified by its tax code
- Each seller, in addition to being defined by its own characterizing attributes, is provided with its own identification code called vendor code represented by a string of 10 characters in alphanumeric format
- All data must be captured (insert, edit, delete) and displayed using the keyboard
- Acquired data, properly processed, must be made persistent on file.
- Data from rented smartphones must be serialized before closing the application.
- The application allows the list of smartphones sold by a specific seller; the list must be sorted according to the following pair of parameters:
  - date of sale (the least recent sale)
  - sale price (the highest price)
- The application allows obtaining a file printout of the list of smartphones rented up to the current day.
- This list must be sorted by the last name of the employee who made the rental; the list will show for
each rental the last name of the employee and all the information of the rented smartphone.

