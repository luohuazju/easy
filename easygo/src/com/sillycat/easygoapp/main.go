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

type BugResult struct {
	Bugs   []Bug
	Status string
}

func getBug(w http.ResponseWriter, r *http.Request) {
	b1 := Bug{Id: "1", BugNumber: "bug1", BugName: "bug1", BugDesn: "desn1"}
	re := BugResult{Status: "Ok", Bugs: []Bug{b1}}
	b, err := json.Marshal(re)
	if err != nil {
		fmt.Fprint(w, "json err: %s", err)
	}
	fmt.Fprint(w, string(b))
}

func updateBug(w http.ResponseWriter, r *http.Request) {
	var b Bug
	c, err := ioutil.ReadAll(r.Body)
	fmt.Println("updateBug called with Body=" + string(c))

	json.Unmarshal(c, &b)

	fmt.Println("updateBug called with Id=" + b.Id)
	fmt.Println("updateBug called with BugNumber=" + b.BugNumber)
	fmt.Println("updateBug called with BugName=" + b.BugName)
	fmt.Println("updateBug called with BugDesn=" + b.BugDesn)

	re := BugResult{Status: "OK", Bugs: []Bug{b}}
	re_json, err := json.Marshal(re)
	if err != nil {
		fmt.Fprint(w, "json err: %s", err)
	}
	fmt.Fprint(w, string(re_json))
}

func deleteBug(w http.ResponseWriter, r *http.Request) {
	id := mux.Vars(r)["Id"]
	fmt.Println("deleteBug called with Id = ", id)

	re := BugResult{Status: "OK"}
	b, err := json.Marshal(re)
	if err != nil {
		fmt.Fprint(w, "json err: %s", err)
	}
	fmt.Fprint(w, string(b))
}

func addBug(w http.ResponseWriter, r *http.Request) {
	var b Bug
	content, err := ioutil.ReadAll(r.Body)
	fmt.Println("addBug called with Body=" + string(content))

	json.Unmarshal(content, &b)
	fmt.Println("addBug called with BugNumber=" + b.BugNumber)
	fmt.Println("addBug called with BugName=" + b.BugName)
	fmt.Println("addBug called with BugDesn=" + b.BugDesn)

	re := BugResult{Status: "OK", Bugs: []Bug{b}}
	re_json, err := json.Marshal(re)
	if err != nil {
		fmt.Fprint(w, "json err: %s", err)
	}
	fmt.Fprint(w, string(re_json))
}

func listBug(w http.ResponseWriter, r *http.Request) {
	b1 := Bug{Id: "1", BugNumber: "bug1", BugName: "bug1", BugDesn: "desn1"}
	b2 := Bug{Id: "2", BugNumber: "bug2", BugName: "bug2", BugDesn: "desn2"}
	re := BugResult{Status: "Ok", Bugs: []Bug{b1, b2}}
	b, err := json.Marshal(re)
	if err != nil {
		fmt.Fprint(w, "json err: %s", err)
	}
	fmt.Fprint(w, string(b))
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
