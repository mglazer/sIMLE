sIMLE - Simple IML Editor
=========================


Building Instructions
---------------------

Type: 

    mvn jetty:run

To start sIMLE. Then open your web browser and go to:

    http://localhost:8080/sIMLE

There are 3 logins defined:

    itecuser:password
    itecadmin:password
    admin:password

Each has a different set of permissions. itecuser can only read, itecadmin can
only work with the ITEC Lab, and admin can do pretty much anything (but at the
moment the permissions systems still needs to be improved).

It is recommended that you get SpringSource ToolSuite and Spring Roo to do
anything with the code:

    http://www.springsource.com/products/sts
    http://www.springsource.org/roo

Although Roo does not really buy us much any more since the basic framework is
setup, it still is nice for automatically generating getter and setter methods
for fields, and for some finder methods. SpringSource Tool Suite is almost
essential as it automatically includes a lot of the functionality needed for
viewing all the aspect weavings that Spring Roo is doing in the background.


Author: Mike Glazer
E-Mail: [mike.glazer@gmail.com](mailto:mike.glazer@gmail.com)

