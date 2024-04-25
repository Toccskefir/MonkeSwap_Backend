# MonkeSwap Backend
The backend application for MonkeSwap<sup>TM</sup> software created in [Spring Boot](https://spring.io/projects/spring-boot) framework

## Getting started
After you clone the code:
  1. Start a mysql server on localhost port 3306 ( or edit the [application.properties](src/main/resources/application.properties) file)
  2. Create an empty database called **monkeswap**
  3. Run the application

## Documentation
You can find OpenAPI documentations for the application under `/docs` endpoint:</br>
 1. Documentation for OpenAPI  `/docs/openapi`
 2. Documentation for endpoints `/docs/endpoints`

## Database
![MonkeSwap_Database](https://github.com/Toccskefir/MonkeSwap_Backend/assets/91217116/62d74b84-d1d3-4412-988a-f7a4f5318aa3)
### users table
| Row name             | Row description                                        |
| -------------------- | ------------------------------------------------------ |
| id                   | Auto incremented id for the user (primary key)         |
| email                | Email of the user used for authentication (unique key) |
| username             | Username of the user (unique key)                      |
| password             | Password of the user used for authentication           |
| trades_completed     | The number of comleted trades by the user              |
| role                 | The role of the user (enum)                            |
| date_of_registration | The date when the user registered                      |
| fullname             | Full name of the user                                  |
| date_of_birth        | The date when the user born                            |
| phonenumber          | Phonenumber of the user                                |
| profile_picture      | Profile picture of the user stored in bytes            |

### items table
| Row name             | Row description                                        |
| -------------------- | ------------------------------------------------------ |
| id                   | Auto incremented id for the item (primary key)         |
| title                | Title of the item                                      |
| item_picture         | Picture of the item stored in bytes                    |
| description          | Description of the item                                |
| views                | The number of times other users viewed the item        |
| reports              | An array of user ids who reported the item             |
| state                | The current state of the item (enum)                   |
| category             | Category of the item (enum)                            |
| price_tier           | Price tier of the item (number between 1 and 5)        |
| user_id              | The id of the user who owns the item (foreign key)     |

### trade_offers table
| Row name             | Row description                                        |
| -------------------- | ------------------------------------------------------ |
| id                   | Auto incremented id for the trade offer (primary key)  |
| offered_item_id      | The id of the offered item (foreign key)               |
| incomin_item_id      | The id of the incoming item (foreign key)              |
| comment              | Comment sent with the trade offer                      |

### notifications table
| Row name             | Row description                                        |
| -------------------- | ------------------------------------------------------ |
| id                   | Auto incremented id for the notification (primary key) |
| message              | Message of the notification                            |
| type                 | Type of the notification                               |
| user_id              | The id of the user, the notification sent to           |
