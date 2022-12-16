## 07.12.2022
- diagram UML
## 09.12.2022
- Added classes for services, controllers and thumbnailGenerator (all withouth implementation)
## 11.12.2022
- DB with DAO
## 12.12.2022
- Entity changes (OneToOne, cascade)
## 13.12.2022
- Added rxJava to backend and created reactive api
## 14.12.2022
## 15.12.2022
- Changed sqlLite dialect to H2Dialect (REASON: errors with sql syntax)
- DAO changed to JPA Rpository (REASON: found session error that duplicates data when using DAO)
## 16.12.2022
- Fixed the issue with queing tasks (generating thumbnails was blocking api response)