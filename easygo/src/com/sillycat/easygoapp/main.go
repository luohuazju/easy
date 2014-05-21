package main

import (
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"io/ioutil"
	"net/http"
)

type Bug struct {
	Id        string
	BugNumber string
	BugName   string
	BugDesn   string
}

type JSONResult map[string]interface{}

func (r JSONResult) String() (s string) {
	b, err := json.Marshal(r)
	if err != nil {
		s = "json err: " + err.Error()
		return
	}
	s = string(b)
	return
}

func ParseJSON(r *http.Request) Bug {
	var b Bug
	c, _ := ioutil.ReadAll(r.Body)
	fmt.Println("ParseJSON called with Body=" + string(c))

	json.Unmarshal(c, &b)
	return b
}

func getBug(w http.ResponseWriter, r *http.Request) {
	b1 := Bug{Id: "1", BugNumber: "bug1", BugName: "bug1", BugDesn: "desn1"}

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprint(w, JSONResult{"status": "ok", "bugs": []Bug{b1}})
}

func updateBug(w http.ResponseWriter, r *http.Request) {
	b := ParseJSON(r)

	fmt.Println("updateBug called with Id=" + b.Id)
	fmt.Println("updateBug called with BugNumber=" + b.BugNumber)
	fmt.Println("updateBug called with BugName=" + b.BugName)
	fmt.Println("updateBug called with BugDesn=" + b.BugDesn)

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprint(w, JSONResult{"status": "ok", "bugs": []Bug{b}})
}

func deleteBug(w http.ResponseWriter, r *http.Request) {
	id := mux.Vars(r)["Id"]
	fmt.Println("deleteBug called with Id = ", id)

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprint(w, JSONResult{"status": "ok"})
}

func addBug(w http.ResponseWriter, r *http.Request) {
	b := ParseJSON(r)

	fmt.Println("addBug called with BugNumber=" + b.BugNumber)
	fmt.Println("addBug called with BugName=" + b.BugName)
	fmt.Println("addBug called with BugDesn=" + b.BugDesn)

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprint(w, JSONResult{"status": "ok", "bugs": []Bug{b}})
}

func listBug(w http.ResponseWriter, r *http.Request) {
	b1 := Bug{Id: "1", BugNumber: "bug1", BugName: "bug1", BugDesn: "desn1"}
	b2 := Bug{Id: "2", BugNumber: "bug2", BugName: "bug2", BugDesn: "desn2"}

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprint(w, JSONResult{"status": "ok", "bugs": []Bug{b1, b2}})
}

func main() {
	router := mux.NewRouter()
	router.HandleFunc("/bugs", listBug).Methods("GET")
	router.HandleFunc("/bugs", addBug).Methods("POST")
	router.HandleFunc("/bugs/{Id}", getBug).Methods("GET")
	router.HandleFunc("/bugs/{Id}", updateBug).Methods("PUT")
	router.HandleFunc("/bugs/{Id}", deleteBug).Methods("DELETE")

	http.Handle("/", router)
	http.ListenAndServe(":8088", nil)
}
