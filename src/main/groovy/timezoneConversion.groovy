import java.time.*
import java.time.format.DateTimeFormatter
import java.time.ZoneId

// Original time in UTC
String utcTime = "2024-08-24T15:30:30Z"

// Parse the time
ZonedDateTime utcDateTime = ZonedDateTime.parse(utcTime, DateTimeFormatter.ISO_ZONED_DATE_TIME)

// Convert to EDT timezone
ZonedDateTime edtDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("America/New_York"))

// Format the time to the desired format
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ssa")
String formattedTime = edtDateTime.format(formatter).toLowerCase()

println formattedTime