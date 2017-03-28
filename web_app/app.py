from flask import Flask, redirect, url_for, request
import sqlite3

conn = sqlite3.connect("auth.db")
print "Opened database successfully!";

# conn.execute('CREATE TABLE user(emp_id TEXT PRIMARY KEY, name TEXT, email TEXT, phone TEXT, password TEXT)')
# print "Table created successfully!";
conn.close();

app = Flask(__name__)

@app.route('/')
def index():
	return 'Authentication API'
	# return "<!DOCTYPE html> <html><head><title>Authentication API</title></head><body></body></html>"

@app.route('/register', methods = ["GET", "POST"])
def register():
	try:
		emp_id = request.args["emp_id"]
		password = request.args["password"]
		phone = request.args["phone"]
		email = request.args["email"]
		name = request.args["name"]

		with sqlite3.connect("auth.db") as con:
			cur = con.cursor()
			cur.execute("INSERT INTO user (emp_id, name, email, phone, password) VALUES (?,?,?,?,?)", (emp_id, name, email, phone, password))
			con.commit()
			msg = '{"result":"user registered successfully"}'
			
	except Exception, e:
		print e
		con.rollback()
		msg = '{"result":"User already registered"}'
	finally:
		return msg
		con.close()

@app.route('/login', methods = ["GET", "POST"])
def login():
	try:
		emp_id = request.args["emp_id"]
		password = request.args["password"]

		with sqlite3.connect("auth.db") as con:
			cur = con.cursor()
			cur.execute("SELECT * FROM user WHERE emp_id=? AND password=?", (emp_id, password))
			a = cur.fetchone()

			if a is not None:
				msg = '{"result": "success", "emp_id": "%s", "name": "%s", "email": "%s", "phone": "%s"}' %(a[0], a[1], a[2], a[3])
			else:
				msg = '{"result": "invalid"}'
				print msg

			
	except Exception, e:
		print "hello"
		print e
		con.rollback()
		msg = '{"result":"error"}'
	finally:
		return msg
		con.close()

			
if __name__ == '__main__':
	app.run("0.0.0.0",8080, debug=True)