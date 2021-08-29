CREATE (student1:Student {
  name:      'Gabriel',
  country:   'EUA',
  birthYear: 1999
})
CREATE (student2:Student {
  name:      'Ana',
  country:   'EUA',
  birthYear: 1999
})
CREATE (student3:Student {
  name:      'Daniel',
  country:   'China',
  birthYear: 1997
})
CREATE (student4:Student {
  name:      'Beatriz',
  country:   'Brazil',
  birthYear: 1990
})

CREATE (department1:Department {name: 'Eletrical Engineering'})
CREATE (department2:Department {name: 'Computer Engineering'})
CREATE (department3:Department {name: 'Science Computer'})
CREATE (department4:Department {name: 'Applied Mathematics'})

CREATE (student1)-[:BELONGS_TO]->(department1)
CREATE (student2)-[:BELONGS_TO]->(department2)
CREATE (student3)-[:BELONGS_TO]->(department3)
CREATE (student4)-[:BELONGS_TO]->(department4)

CREATE (subject1:Subject {name: 'Algorithm I'})
CREATE (subject2:Subject {name: 'Algorithm II'})
CREATE (subject3:Subject {name: 'Data Structure I'})
CREATE (subject4:Subject {name: 'Data Structure II'})
CREATE (subject5:Subject {name: 'Calculus I'})
CREATE (subject6:Subject {name: 'Calculus II'})

CREATE (student1)-[:IS_LEARNING {marks: 80}]->(subject1)
CREATE (student1)-[:IS_LEARNING {marks: 25}]->(subject2)
CREATE (student1)-[:IS_LEARNING {marks: 32}]->(subject3)
CREATE (student2)-[:IS_LEARNING {marks: 70}]->(subject2)
CREATE (student2)-[:IS_LEARNING {marks: 60}]->(subject1)
CREATE (student3)-[:IS_LEARNING {marks: 50}]->(subject1)
CREATE (student3)-[:IS_LEARNING {marks: 18}]->(subject4)
CREATE (student3)-[:IS_LEARNING {marks: 25}]->(subject5)
CREATE (student3)-[:IS_LEARNING {marks: 27}]->(subject6)
CREATE (student4)-[:IS_LEARNING {marks: 21}]->(subject1)
CREATE (student4)-[:IS_LEARNING {marks: 67}]->(subject2)




