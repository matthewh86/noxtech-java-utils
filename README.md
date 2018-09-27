# noxtech-java-utils

[![CircleCI](https://circleci.com/gh/noxtech/noxtech-java-utils.svg?style=svg)](https://circleci.com/gh/noxtech/noxtech-java-utils)
[![Download](https://api.bintray.com/packages/noxtech/maven/noxtech-java-utils/images/download.svg) ](https://bintray.com/noxtech/maven/noxtech-java-utils/_latestVersion)

## Java utils

### Dates
* DateUtils
  * Converts dates between:
    - java.time <-> JodaTime
* LegacyDateUtils
  * Converts dates between:
    - java.util.Date <-> JodaTime
    - java.util.Date <-> java.time
  * Compares java.util.Date

### Json
* GeoJson Serializers / Deserializers for:
  * Point
  * Line
  * Polygon

### Misc Utils
* Elvis - wraps chained method invocations in Optional
* ResourceUtil - convenience methods for reading resources
