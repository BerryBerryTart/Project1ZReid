# Project 1: Managed Reimbursment With Authentication

### Written by Zuberi Reid

## Project Description
This is a basic app allows employees to submit reimbursment tickets. It also allows company managers to approve / deny and even view all tickets.

## Technology Used
- Java 1.8
- Javalin
- JDBC
- Logback / SLF4J
- JUnit
- Mockito
- MariaDB
- Hibernate

## General Endpoints
- `POST /login_acc` : allows user to log in
    - Will display an error if login credentials are incorrect
- `POST /register_acc` : allows user to register an account
- `GET /current_user`: returns the current logged in user if one
- `POST /logout_acc` : destroys associated account session and logs user out

#### Related Fields
- `users_id`
    - Generated on creation
    - Uniquely identifies a user
- `username`
    - **Required on login**
    - Required to **register**
- `password`
    - **Required on login**
    - Required to **register**
    - Securely hashed with SHA-256
- `first_name`
    - Only required to **register**
- `last_name`
    - Only required to **register**
- `email`
    - Only required to **register**
- `role_id`
    - Defaults to `EMPLOYEE` on creation
    - Used to related a user to their permissive role
    - Of type `EMPLOYEE` or `MANAGER`

## Employee Endpoints
Managers rank above employees and thus also have access to these

- `POST /add_ticket` : allows user to submit a ticket
- `GET /get_ticet_status` : allows user to view all their submitted tickets
- `GET /get_ticet_status/:id` : allows user to view a single ticket of theirs in more detail
- `GET /ticket_blob/:id` : Fetches the ticket blob data
    - Only used to fetch individual tickets to reduce bandwidth when fetching all tickets

#### Related Fields
- `reimb_id`
    - Generated on creation
    - Uniquely identifies a ticket
- `amount`
    - **Required for submission**
    - Reimbursement amount requested by user as a decimal
- `description`
    - **Required for submission**
    - 250 character limit
    - Short description of ticket
- `receipt`
    - **Required for submission**
    - Byte array of the image of the receipt
    - Must be either a jpeg, png or gif
- `submitted`
    - Timestamp generated on creation
- `resolved`
    - Timestamp generated on ticket status update
- `author_id`
    - Relates ticket to the user who submitted it
- `resolver_id`
    - Relates ticket to the user who resolved it
- `status_id`
    - Status type of ticket
    - Either `COMPLETED` or `REJECTED`
- `type_id`
    - **Required for submission**
    - Classification of ticket
    - Must be `BUISNESS`, `RELOCATION`, `OTHER`

## Manager Endpoints

These endpoints are only visible if you have a manager role. If not the user will be prompted with a permission error.

- `GET /view_ticket` : Allows a manger to view tickets from everyone
- `GET /view_ticket/:id` : Allows a manager to view a ticket in detail
    - They will be able to see the receipt here
- `PUT /update_ticket_/:id` : Allows a manager to either approve or deny a ticket