# STEP 1 - Project setup (detailed instructions)

This folder contains files to complete **Step 1: Create working folder & prepare files** for the Cold Chain Tracking System.

Files created:
- schema.sql        -> Database schema (create database and tables, stored procedures)
- seed_data.sql    -> Sample seed data (roles, demo users, warehouses, sample logs)
- db.properties.example -> Example database connection properties
- README_STEP1.md  -> This file (instructions)

## What you must do now (Windows steps)

1. Create a folder on your PC, e.g.:
   `C:\projects\coldchain-tracker`

2. Copy the files from this folder (`/mnt/data/coldchain-step1`) to your project folder.
   - If using Windows Explorer, open the path and copy files to `C:\projects\coldchain-tracker`.

3. Install MySQL Community Server 8.x and MySQL Workbench if not already installed.

4. Open MySQL Workbench or Command Prompt:
   - To use Command Prompt:
     ```
     mysql -u root -p
     ```

5. Run the schema script:
   - From MySQL Workbench: File → Open SQL Script → select `schema.sql` → Click lightning bolt (execute).
   - From MySQL CLI:
     ```
     SOURCE C:/projects/coldchain-tracker/schema.sql;
     ```

6. Run the seed script:
   - Similarly run `seed_data.sql`.

7. Create an application user (recommended) using MySQL CLI:
   ```
   CREATE USER 'coldchain_app_user'@'localhost' IDENTIFIED BY 'YourStrongPassword';
   GRANT SELECT, INSERT, UPDATE, DELETE ON coldchain.* TO 'coldchain_app_user'@'localhost';
   GRANT EXECUTE ON PROCEDURE coldchain.AddTemperatureLogWithAlert TO 'coldchain_app_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

8. Copy `db.properties.example` to your Java project resources as `src/main/resources/db.properties` and update the password.

## Notes & security
- The seed user password_hash values are placeholders. For demos, you can temporarily set known hash values. For real use, always store bcrypt hashes and never plaintext passwords.
- If an alert does not appear automatically, the stored procedure is provided and the application can call it when inserting temperature logs.

If you want, I can now:
- Provide a ready-to-run `db.properties` configured with a password you choose (do NOT share real passwords publicly).
- Help you run the SQL on your machine by giving exact commands for Windows PowerShell or MySQL Workbench.