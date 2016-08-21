CREATE TABLE "Task" 
(
    "Task_id" INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),  
    "Project_id" INT, 
    "Name" VARCHAR (50),
    "WorkHours" SMALLINT,
    "StartDate" DATE DEFAULT '0000-00-00',
    "EndDate" DATE DEFAULT '0000-00-00',
    "Status" VARCHAR (50),
	FOREIGN KEY ( "Project_id" ) REFERENCES "Project" ( "Project_id" )
            );
			
			
CREATE TABLE "Project" 
(
    "Project_id" INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),  
    "Name" VARCHAR (150),
    "ShortName" VARCHAR (25),
    "Description" VARCHAR (500)
            );

			
CREATE TABLE "Employee" 
(
    "Employee_id" INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),  
    "FirstName" VARCHAR (50),
    "LastName" VARCHAR (50),
    "Position" VARCHAR (150)
            );
			
CREATE TABLE "Emp_Task" 
(
    "Employee_id" INT not null,
    "Task_id" INT not null,
    PRIMARY KEY ("Employee_id", "Task_id"),
    FOREIGN KEY ("Employee_id") REFERENCES "Employee" ("Employee_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY ("Task_id") REFERENCES "Task" ("Task_id") ON DELETE NO ACTION ON UPDATE NO ACTION
            );
			

/**The creates for database in postgreSQL
 */

CREATE TABLE "Project" 
(
    "Project_id" SERIAL NOT NULL primary key,  
    "Name" VARCHAR (150) NOT NULL,
    "ShortName" VARCHAR (25) NOT NULL,
    "Description" VARCHAR (500)
            );

			
CREATE TABLE "Employee" 
(
    "Employee_id" SERIAL NOT NULL primary key,  
    "FirstName" VARCHAR (50) NOT NULL,
    "LastName" VARCHAR (50) NOT NULL,
    "Position" VARCHAR (150)
            );

CREATE TABLE "Task" 
(
    "Task_id" SERIAL NOT NULL primary key, 
    "Project_id" INT, 
    "Name" VARCHAR (50) NOT NULL,
    "WorkHours" SMALLINT,
    "StartDate" DATE,
    "EndDate" DATE,
    "Status" VARCHAR (50) CHECK ("Status" IN ('Done', 'In progress', 'Postpone','Not started')),
	FOREIGN KEY ( "Project_id" ) REFERENCES "Project" ( "Project_id" ) ON DELETE CASCADE ON UPDATE CASCADE
            );

CREATE TABLE "Emp_Task" 
(
    "Employee_id" INT NOT NULL,
    "Task_id" INT NOT NULL,
    PRIMARY KEY ("Employee_id", "Task_id"),
    FOREIGN KEY ("Employee_id") REFERENCES "Employee" ("Employee_id") ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY ("Task_id") REFERENCES "Task" ("Task_id") ON DELETE CASCADE ON UPDATE CASCADE
            );