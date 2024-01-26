INSERT INTO person (id, name, surname, age, gender, nation) VALUES
                                                                (nextval('person_seq'), 'John', 'Doe', 30, 0, 'USA'),
                                                                (nextval('person_seq'), 'Jane', 'Doe', 25, 0, 'Canada'),
                                                                (nextval('person_seq'), 'Alice', 'Smith', 28, 1, 'UK');