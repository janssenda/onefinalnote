One Final Note

                One Final Note is a music themed blog and is the result of a final capstone group project
                at The Software Guild, built by a team of three. The app was designed around a spec sheet
                from an imaginary customer
                who wanted a personal blog website. An interesting characteristic of the site is an
                ability to expand itself at the owners discretion. A static page feature is built in
                which allows the owner to write new pages for the site without writing any HTML code,
                and add them to the normal structure. A WYSWIG editor called Froala was used to accomplish
                this. Froala generates HTML code, which is stored along with relevant information in the
                database. The pages are then generated and served upon request for the front end. All posts
                and pages have states of published and unpublished, allowing for flexible editing and timed
                posts.

                Spring security is employed to provide 4 access levels to serve the owner and site admins.
                A lower level role allows anyone visiting the site to create an account so they can participate
                in comments on all the blog posts. Comments can be moderated by site admins. A role management
                panel allows admins and owners to manage all users and adjust their roles or disable them.
               
                Because Spring security was a requirement, the app was built largely on a JSP/JSTL framework,
                with accompanying javascript for smooth front end functionality. All data is stored on a
                MySQL server and is managed through a standard MVC java app using Spring/JDBC.
