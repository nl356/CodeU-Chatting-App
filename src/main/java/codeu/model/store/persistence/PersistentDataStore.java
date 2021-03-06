// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.persistence;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import com.google.appengine.api.datastore.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new of modified objects back to the Datastore.
 */
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;
  private KeyFactory ConvoFactory;
  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *                                      Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {

    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String userName = (String) entity.getProperty("username");
        String password = (String) entity.getProperty("password");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        User user = new User(uuid, userName, password, creationTime);
        users.add(user);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads unique User object using UUID from the Datastore service and returns it.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *                                      Datastore service
   */
  public User loadUser(UUID id) throws PersistentDataStoreException {

    // Retrieve all users where uuid matches given UUID
    Query query = new Query("chat-users");
    query.setFilter(new Query.FilterPredicate("uuid", Query.FilterOperator.EQUAL, id.toString()));
    PreparedQuery results = datastore.prepare(query);

    // There should only be one user for each UUID
    Entity entity = results.asSingleEntity();
    try {
      UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
      String userName = (String) entity.getProperty("username");
      String password = (String) entity.getProperty("password");
      Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
      User user = new User(uuid, userName, password, creationTime);
      return user;
    } catch (Exception e) {
      // In a production environment, errors should be very rare. Errors which may
      // occur include network errors, Datastore service errors, authorization errors,
      //  database entity definition mismatches, or service mismatches.
      throw new PersistentDataStoreException(e);
    }
  }

  /**
   * Loads unique User object using username from the Datastore service and returns it.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *                                      Datastore service
   */
  public User loadUser(String name) throws PersistentDataStoreException {

    // Retrieve all users where uuid matches given UUID
    Query query = new Query("chat-users");
    query.setFilter(new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, name));
    PreparedQuery results = datastore.prepare(query);

    //There should only be one user for each UUID
    Entity entity = results.asSingleEntity();
    try {
      UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
      String userName = (String) entity.getProperty("username");
      String password = (String) entity.getProperty("password");
      Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
      User user = new User(uuid, userName, password, creationTime);
      return user;
    } catch (Exception e) {
      // In a production environment, errors should be very rare. Errors which may
      // occur include network errors, Datastore service errors, authorization errors,
      //  database entity definition mismatches, or service mismatches.
      throw new PersistentDataStoreException(e);
    }
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *                                      Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {

    List<Conversation> conversations = new ArrayList<>();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime);
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *                                      Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public int getDailyMessageCountFor(UUID id) throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages");
    query.setFilter(new Query.FilterPredicate("author_uuid", Query.FilterOperator.EQUAL, id.toString()));
    PreparedQuery results = datastore.prepare(query);

    int messageCounter = 0;
    // Instant 24 hours ago.
    Instant before24 = Instant.now().minusSeconds(24 * 60 * 60);

    for (Entity entity : results.asIterable()) {
      try {
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));

        // If the time of the message is after the time 24 hours before, inc daily message counter.
        if(creationTime.isAfter(before24));
          messageCounter++;

      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messageCounter;
  }

  /** Write a User object to the Datastore service. */

  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users");
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("password", user.getPassword());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    datastore.put(userEntity);
  }

  /**
   * Write a Message object to the Datastore service.
   */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages");
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /**
   * Write a Conversation object to the Datastore service.
   */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations", conversation.getTitle());
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    datastore.put(conversationEntity);
  }

  public void delete(Conversation conversation){
    String convoTitle = conversation.getTitle();
    System.out.println(convoTitle);
    Key key = KeyFactory.createKey("chat-conversations", convoTitle);
    datastore.delete(key);
  }
}