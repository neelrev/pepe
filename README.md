# pepe
A wholistic framework for end-to-end implementation of GDPR compliance

GDPR is an extremely important compliance about the protection of personal data and a binding legislation which is enforced from 
May 25, 2018 for any organization with users from EU. Early this year, when I started working on GDPR, I realised that when it 
comes to actual hands-on implementation of GDPR, internet is surprisingly quiet. Most of the articles talk about disjointed topics 
ranging from getting consents from users to showing cookie banners etc. There is a lack of a wholistic solution combining all 
important aspects of GDPR, ranging from data privacy to cookie and consent management. One can not easily find any information on 
how to design and implement a GDPR system for an existing application, web or mobile. GDPR has become a blackbox.

Pepe hopes to fill in this gap. Pepe aims to become a wholistic framework for end-to-end implementation of GDPR compliance for a web application.

![alt Focus Areas of GDPR](https://github.com/neelrev/pepe/blob/master/Pillars%20of%20GDPR.PNG)

## Design of Pepe
The core of Pepe is based on three fundamental principles - loose coupling, scalability and robustness. Pepe is at it's infancy 
right now and need to continuously evolve. With a lego architecture which can be easily extended or stripped off, Pepe can be 
transformed at will.

An integral part of Pepe is a layer of microservices. These services can be deployed in your home server for your personal website, 
or in cloud so that they can scale up to serve multiple web applications hosted by an entire organization.

Pepe is designed to be autonomus and work with minimal user interaction. At the core of Pepe is a vigilant job subsystem 
which fetches data and checks the status of GDPR enablement day after day.


![alt Design of Pepe](https://github.com/neelrev/pepe/blob/master/Design%20of%20Pepe.PNG)
