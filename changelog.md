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
- Added frontend template
- Added Retrofit configuration
## 15.12.2022
- Changed sqlLite dialect to H2Dialect (REASON: errors with sql syntax)
- DAO changed to JPA Rpository (REASON: found session error that duplicates data when using DAO)
- Added posibility to send zip
- Fixed fileChooser from showSaveDialod to showOpenDialog
- Added ProgressBar indicator
- Changed coding byte[] to Base64.encodeBase64String
- Added GridPane with Pictures
## 16.12.2022
- Fixed the issue with queing tasks (generating thumbnails was blocking api response)
- Added HttpStatus.PROCESSING status code for resizing in progress
- Added possibility to show bigger picture on click
- Added opening bigger picture in modality WINDOW_MODAL
- Added ImageDto for incoming responses in frontend
- Added possibility to send png, jpg, bmp
- Added fake rezising image simulation as waiting random time from 2 to 10 seconds
## 08.01.2023
- Added directroy watcher (faulty)
## 09.01.2023
- Preparing Entities for thumbnails in multiple sizes
## 15.01.2023
    added combobox for picture sizes

## 16.01.2023
- Generating thumbnails in all sizes after receiving image
- server restart queries
## 17.01.2023
- Server restart done
- added retyr callback using backoff.decolerrated jitter
- added retry policy builder
- added path controller
- change api schema call to directorycontent

## 19.01.2023
- Directory Watcher fixed and done
- move findirectories logic to sql query
- added a-zA-Z0-9 patterm for directories namess
