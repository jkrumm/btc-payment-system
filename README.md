# BTCPaymentSystem

Author: Johannes Krumm

This project is part of my bachelors thesis together with Rubean AG: **Implementation of a prototype for payment processing with Bitcoin and evaluating the integration into PhonePos**

This Java application listens to the Bitcoin Blockchain. It manages a wallet and generates new addresses to receive payments. 

**Unique is the way of validating a transaction as secure as possible before the first block containing the transcation has been validated by a miner.**

The corresponding frontend can be found here: [btc-payment-system-frontend](https://github.com/jkrumm/btc-payment-system-frontend)

A simplified POC application can be found here: [btc-payment-system-poc](https://github.com/jkrumm/btc-payment-system-poc)

---

#### Running the Application

`./gradlew` (on Mac OS X/Linux) of `gradlew` (on Windows)

(this will run our default Gradle task, bootRun)

The application will be available on http://localhost:8080.

If you will use live reload with npm start or yarn start then you can speed up server start up by excluding webpack tasks by:

`./gradlew -x webapp`

Alternatively, if you have installed Gradle, you can launch the Java server with Gradle:

`gradle`

If you want more information on using Gradle, please go to https://gradle.org

---

This application was generated using JHipster 7.0.0-beta.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v7.0.0-beta.1](https://www.jhipster.tech/documentation-archive/v7.0.0-beta.1).
