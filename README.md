# Practical Cloud-Native Java Development with MicroProfile

<a href="https://www.packtpub.com/cloud-networking/practical-cloud-native-java-development-with-microprofile?utm_source=github&utm_medium=repository&utm_campaign=9781801078801"><img src="https://static.packt-cdn.com/products/9781801078801/cover/smaller" alt="Practical Cloud-Native Java Development with MicroProfile" height="256px" align="right"></a>

This is the code repository for [Practical Cloud-Native Java Development with MicroProfile](https://www.packtpub.com/cloud-networking/practical-cloud-native-java-development-with-microprofile?utm_source=github&utm_medium=repository&utm_campaign=9781801078801), published by Packt.

**Develop and deploy scalable, resilient, and reactive cloud-native applications using MicroProfile 4.1**

## What is this book about?
In this cloud-native era, most applications are deployed in a cloud environment that is public, private, or a combination of both. To ensure that your application performs well in the cloud, you need to build an application that is cloud native. MicroProfile is one of the most popular frameworks for building cloud-native applications. As an open standard technology, MicroProfile helps improve application portability across all of MicroProfile's implementations. 

This book covers the following exciting features:
- Understand best practices for applying the 12-Factor methodology while building cloud-native applications.
- Create client-server architecture using MicroProfile Rest Client and JAX-RS.
- Configure your cloud-native application using MicroProfile Config.
- Secure your cloud-native application with MicroProfile JWT.
- Become well-versed with running your cloud-native applications in Open Liberty.
- Grasp MicroProfile Open Tracing and learn how to use Jaeger to view trace spans.
- Deploy Docker containers to Kubernetes and understand how to use ConfigMap and Secrets from Kubernetes.

If you feel this book is for you, get your [copy](https://www.amazon.com/dp/1801078807) today!

## Instructions and Navigations
All of the code is organized into folders. For example, Chapter02.

The code will look like the following:
```
@Provider
public class ColorParamConverterProvider implements ParamConverterProvider {

@Override
public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
    if (rawType.equals(Color.class)) {
        return (ParamConverter<T>) new ColorParamConverter();
    }
  return null;
 }
}
```

**Following is what you need for this book:**
This book is for Java application developers and architects looking to build efficient applications using an open standard framework that performs well in the cloud. DevOps engineers who want to understand how cloud-native applications work will also find this book useful. A basic understanding of Java, Docker, Kubernetes, and cloud is needed to get the most out of this book

With the following software and hardware list you can run all code files present in the book (Chapter 1-12).
### Software and Hardware List
| Chapter | Software required | OS required |
| -------- | ------------------------------------ | ----------------------------------- |
| 4, 6, 11 | JDK, Apache Maven, Open Liberty Server | Windows, Mac OS X, and Linux (Any) |
| 7 | JDK, Apache Maven, Git client, Docker client, OpenShift client  | Windows, Mac OS X, and Linux (Any) |


We also provide a PDF file that has color images of the screenshots/diagrams used in this book. [Click here to download it](https://static.packt-cdn.com/downloads/9781801078801_ColorImages.pdf).

### Related products
* Jakarta EE Cookbook - Second Edition [[Packt]](https://www.packtpub.com/product/jakarta-ee-cookbook-second-edition/9781838642884?utm_source=github&utm_medium=repository&utm_campaign=9781838642884) [[Amazon]](https://www.amazon.com/dp/1838642889)

* Supercharge Your Applications with GraalVM [[Packt]](https://www.packtpub.com/product/supercharge-your-applications-with-graalvm/9781800564909?utm_source=github&utm_medium=repository&utm_campaign=9781800564909) [[Amazon]](https://www.amazon.com/dp/1800564902)


## Get to Know the Author
**Emily Jiang**
is a Java Champion, a cloud-native architect with practical experience of building cloud-native applications. She is a MicroProfile guru, leading a number of MicroProfile specifications as well as the implementations in Open Liberty. She is a well-known international conference speaker.

**Andrew McCright**
is IBM's Web Services Architect with 20 years of experience building Enterprise Java runtimes. He leads the MicroProfile Rest Client & GraphQL projects and contributes to Open Liberty, Jakarta REST, CXF, RESTEasy, and more. He is also a blogger

**John Alcorn**
is an application modernization architect in the Cloud Engagement Hub, specializing in helping customers modernize their traditional Java EE applications to the cloud. He developed and maintains the Stock Trader application that shows how to build a composite application out of MicroProfile-based microservices in Java. You can connect with John via Twitter.

**David Chan**
is a software developer at IBM who works on the observability and serviceability components of the Open Liberty project. He is involved with the MicroProfile project with a specialization in the MicroProfile Metrics component.

****
### Download a free PDF

 <i>If you have already purchased a print or Kindle version of this book, you can get a DRM-free PDF version at no cost.<br>Simply click on the link to claim your free PDF.</i>
<p align="center"> <a href="https://packt.link/free-ebook/9781801078801">https://packt.link/free-ebook/9781801078801 </a> </p>