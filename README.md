loglist-importer
================

Greetings, traveler!

This is the data importer from MySQL to PostgreSQL for the [LogList Project](https://github.com/codingteam/loglist).

Initially we had the MySQL dump with invalid encoding setting. So if you have one first you need to decode it into the
proper encoding. The `src/ps1/Prepare-Data.ps1` script may give you some initial thoughts on how terribly the data was
malformed and how to fix that.

Secondary trouble is that database stored the rendered HTML and not the original quotes. I've solved it with some tiny
hacks.

The data was imported successfully so I **hope** this project now have only historical value.
