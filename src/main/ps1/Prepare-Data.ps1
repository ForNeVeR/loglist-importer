cmd /c 'iconv -c -f utf-8 -t cp1252 .\loglist_ru.sql > loglist_1252.sql'
cmd /c 'iconv -c -f cp1251 -t utf-8 loglist_1252.sql > loglist_utf8.sql'
# Additional step: manually replace all "latin1" occurrences with "utf8".