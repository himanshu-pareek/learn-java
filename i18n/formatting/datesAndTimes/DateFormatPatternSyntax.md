## Date format pattern syntax

| Symbol |        Meaning       |    Presentation   |        Example        |
|:------:|:--------------------:|:-----------------:|:---------------------:|
| G      | era designator       | Text              | AD                    |
| y      | year                 | Number            | 2009                  |
| M      | month in year        | Text &amp; Number | July &amp; 07         |
| d      | day in month         | Number            | 10                    |
| h      | hour in am/pm (1-12) | Number            | 12                    |
| H      | hour in day (0-23)   | Number            | 0                     |
| m      | minute in hour       | Number            | 30                    |
| s      | second in minute     | Number            | 55                    |
| S      | millisecond          | Number            | 978                   |
| E      | day in week          | Text              | Tuesday               |
| D      | day in year          | Number            | 189                   |
| F      | day of week in month | Number            | 2 (2nd Wed in July)   |
| w      | week in year         | Number            | 27                    |
| W      | week in month        | Number            | 2                     |
| a      | am/pm marker         | Text              | PM                    |
| k      | hour in day (1-24)   | Number            | 24                    |
| K      | hour in am/pm (0-11) | Number            | 0                     |
| z      | time zone            | Text              | Pacific Standard Time |
| '      | escape for text      | Delimiter         | (none)                |
| '      | single quote         | Literal           | '                     |

## Number of symbols and result

| Presentation      | Number of Symbols                    | Result                                                                                                               |
|-------------------|--------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| Text              | 1 - 3                                | abbreviated form, if one exists                                                                                      |
| Text              | &gt;= 4                              | full form                                                                                                            |
| Number            | minimum number of digits is required | shorter numbers are padded with zeros (for a year, if the count of 'y' is 2, then the year is truncated to 2 digits) |
| Text &amp; Number | 1 - 2                                | number form                                                                                                          |
| Text &amp; Number | 3                                    | text form                                                                                                            |

