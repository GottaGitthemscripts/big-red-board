# Big Red Board

A full-stack Nebraska Husker football recruiting board built with **Java (Javalin)**, **PostgreSQL**, and **HTML/JavaScript**.

## Tech Stack
* **Backend:** Java 25, Javalin 6, Maven, Gson, PostgreSQL JDBC
* **Database:** PostgreSQL (`big_red_board`)
* **Frontend:** HTML, CSS, Vanilla JavaScript

## Database Schema
The PostgreSQL database uses three normalized tables:
* `recruits` — Player info (`recruit_id`, `first_name`, `last_name`, `position`, `state`, `grad_class`, `high_school`, `height_inches`, `weight`)
* `schools` — College programs (`school_id`, `school_name`, `abbreviation`, `city`, `state`)
* `offers` — Join table linking recruits to schools (`offer_id`, `recruit_id`, `school_id`, `offer_date`, `is_committed`, `status`)

## How to Run Locally
1. Make sure PostgreSQL is running locally on port `5432` with the `big_red_board` database created.
2. Set your database password as an environment variable in your terminal:
   * **Windows PowerShell:** `$env:DB_PASS="husker123"`
   * **Windows CMD:** `set DB_PASS=husker123`
   * **Mac / Linux:** `export DB_PASS="husker123"`
3. Run `App.java` inside `big-red-board/src/main/java/com/example/App.java`.
4. Open **`http://localhost:7070/`** in your browser (or **`http://localhost:7070/api/recruits`** for raw JSON).

## Next Features to Build
- [ ] Configure `.vscode/launch.json` so `DB_PASS` loads automatically on Run
- [ ] Add interactive filters on `index.html` (by graduation class, position, and committed status)
- [ ] Build a `POST /api/recruits` endpoint and web form to add new recruits and offers from the browser
- [ ] Add a commit counter and position room breakdown at the top of the board
