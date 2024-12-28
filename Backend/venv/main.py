from flask import Flask, jsonify, request
from flask_cors import CORS
from fsrs import *
import mysql.connector

app = Flask(__name__)
cors = CORS(app,origins='*')

mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="",
  database="srs"
)
def convertDate(d):
    format_str = '%d/%m/%Y'
    datetime_obj = datetime.strptime(d, format_str)
    return datetime_obj

def updateCard(schedule, rating):
    f = FSRS()
    if schedule["state"] == 0:
        card = Card()
        #print("card new")
    else :
        cardDue = convertDate(schedule["due"])
        card = Card(cardDue,schedule["stability"],schedule["difficulty"],schedule["elapsedDays"],schedule["scheduledDays"],schedule["lapses"],schedule["state"])
    now = datetime.now()
    scheduling_cards = f.repeat(card, now)
    card = scheduling_cards[rating].card
    #print(card.__dict__)

    mycursor = mydb.cursor()


    due = card.due.strftime('%Y-%m-%d')
    #print(due)

    mycursor.execute(f"UPDATE deck1 SET due = '{due}' WHERE id = " + str(schedule["id"]) + " ;")

    mydb.commit()
    mycursor = mydb.cursor()

    mycursor.execute(f"UPDATE deck1 SET stability = " + str(card.stability) + ", difficulty = " + str(card.difficulty) + ", elapsedDays = " + str(card.elapsed_days) + ", scheduledDays = " + str(card.scheduled_days) + ", lapses = " + str(card.lapses) + ", state = " + str(card.state) + " WHERE id = " + str(schedule["id"]) + " ;")

    mydb.commit()


@app.route("/review", methods=['GET','POST'])
def review():
    input = request.get_json()
    print(input["card"])
    print(input["rating"])
    updateCard(input["card"], input["rating"])
    return jsonify(
        {
            "card": input
        }
    )
    


if __name__ == "__main__":
    app.run(debug=True, port=8080)