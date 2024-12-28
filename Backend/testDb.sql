-- Used to create database for stroring cards and user data

DROP DATABASE IF EXISTS srs;
CREATE DATABASE IF NOT EXISTS srs;
USE srs;

DROP TABLE IF EXISTS card;
CREATE TABLE card (
  front varchar(255) NOT NULL,
  back varchar(255) NOT NULL,
  id int NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS deck1;
CREATE TABLE deck1 (
	due date NOT NULL,
    stability float(5,2) NOT NULL,
    difficulty float(3,2) NOT NULL,
    elapsedDays int(6) NOT NULL,
    scheduledDays float(5,2) NOT NULL,
    lapses int(3) NOT NULL,
    state int(2) NOT NULL,
    lastReview date,
    id int NOT NULL,
    FOREIGN KEY (id) References card(id)
);

INSERT INTO card values
("What are the components of a neuron in a neural network, and how do they contribute to its
function?", "Neurons in a neural network have incoming connections with assigned weights, apply activation
functions, and thresholds. The weights determine how inputs influence the neuron's output.", 1),
("How do neural networks learn and adjust their weights to perform specific tasks?", "Neural networks learn by adjusting their weights through a process called training. During
training, they are provided with examples of inputs and desired outputs, and the weights are updated to
minimize errors and improve performance.", 2),
("How does ChatGPT create 'words' when generating text, and what influences the distribution
of word lengths?", "Words are created by adding spaces based on certain probabilities. The distribution of word
lengths is influenced by English word length statistics.", 3),
("Why is the 'q' column blank in the plot of probabilities for pairs of letters in English text?", "The 'q' column has a zero probability for most letters except when followed by 'u,' reflecting
the common pairing of 'qu' in English.", 4),
("How does ChatGPT address the challenge of estimating word sequence probabilities?", "ChatGPT uses a 'large language model' (LLM) with 175 billion parameters to estimate word
sequence probabilities effectively.", 5);

INSERT INTO deck1 values
('2024-04-03', 0, 0, 0,0, 0, 0,Null, 1),
('2024-04-03', 0, 0, 0,0, 0, 0,Null, 2),
('2024-04-20', 0, 0, 0,0, 0, 0,Null, 3),
('2024-04-03', 0, 0, 0,0, 0, 0,Null, 4),
('2024-04-03', 0, 0, 0,0, 0, 0,Null, 5);

    



