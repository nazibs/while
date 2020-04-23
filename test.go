package main

import "fmt"

const (
	integer = "INTEGER"
	plus = "PLUS"
	minus = "MINUS"
	mul = "MUL"
	div = "DIV"
	space = "SPACE"
	eof = "EOF"
)

type Token struct {
	Type string
	Value string
}

type Lexer struct {
	Text string
	POS int
	Current_char string
}

//func newToken(type string, value string) Token {
//	tok := Token {type, value}
//	return tok
//}


func main(){

	text := "4 + 5"

	lexer = Lexer{Text: text, POS: 0, Current_char: string(text[0])}

	t := Token{Type: integer, Value: "12"}

	//t := newToken(integer, "12")
	fmt.Println(t)
}



