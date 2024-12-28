# SRS-Spring-2024
how to run the code for Backend and Front end with MySQL and React:

(1) First we should install MySQL Workbench, and then set up own username and password for local MySQL Workbench

(2) Go to the "Backend/.env", we need to include own MySQL username and password in the .env file

(3) we should install node.js

(4) open the node.js command prompt, go to "Backend" package, we can type "cd Backend"

(5) then we need to type "npm i mysql2"

(6) then we need to type "npm i dotenv"

(7) then we need to type "node app.js"

(8) open the link "http://localhost:8081/notes", make sure sql file can output tables to backend page

(9) open another node.js command prompt, go to "srs-app" package, we can type "cd srs-app", this is for front end

(10) then we can type "npm install"

(11) then we can type "npm run dev"

(12) open the link "http://localhost:5173/", the sql file can output tables to front end page, make sure both backend (Backend/app.js) and front end (srs-app/src/App.jsx) should run at the same time
