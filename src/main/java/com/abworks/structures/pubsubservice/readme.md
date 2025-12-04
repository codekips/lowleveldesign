# Pub/Sub System (LLD)

## Problem Statement

Design and implement a Publish-Subscribe (Pub/Sub) system that allows publishers to send messages to topics, and subscribers to receive messages from topics they are interested in. The system should support multiple topics, multiple subscribers per topic, and asynchronous message delivery.

---

## Requirements

- **Topics:** The system supports multiple topics.
- **Publishers:** Publishers can publish messages to any topic.
- **Subscribers:** Subscribers can subscribe to one or more topics and receive messages published to those topics.
- **Multiple Subscribers:** Each topic can have multiple subscribers.
- **Asynchronous Delivery:** Messages are delivered to subscribers asynchronously.
- **Unsubscribe:** Subscribers can unsubscribe from topics.
- **Extensibility:** Easy to add new subscriber types or message processing logic.

---
