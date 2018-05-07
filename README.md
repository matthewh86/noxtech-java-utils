# noxtech-java-utils

[![CircleCI](https://circleci.com/gh/noxtech/noxtech-java-utils.svg?style=svg)](https://circleci.com/gh/noxtech/noxtech-java-utils)
[![Download](https://api.bintray.com/packages/noxtech/maven/noxtech-java-utils/images/download.svg) ](https://bintray.com/noxtech/maven/noxtech-java-utils/_latestVersion)

## Java utils

* Elvis - wraps chained method invocations in Optional
* LegacyDateUtils
  * Converts dates between:
    - java.util.Date <-> JodaTime
    - java.util.Date <-> java.time
  * Compares java.util.Date

## Version History

### 0.1.2
* Added circleci workflows for:
  - creating snapshots to oss.jfrog.org
  - deploying releases to bintray
* Configured semantic-build-versioning and gradle-release plugins to work with each other

### 0.1.1
* Added LegacyDateUtils
* Added Elvis

### 0.1.0
* Added net.researchgate.release versioning