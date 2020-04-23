load harness

@test "custom-1" {
  check 'x := 1; y :=    5' '{x → 1, y → 5}'
}

@test "custom-2" {
  check 'x:=10 ; if true then x := x / 2 else x := x / 5' '{x → 5}'
}

@test "custom-3" {
  check 'x:=10 ; if true then y := x / 2 else y := x / 5' '{x → 10, y → 5}'
}

@test "custom-4" {
  check 'x := 10 ; while x > 5 do x := x - 1' '{x → 5}'
}

@test "custom-5" {
  check 'x := 10 ; while x < 100 do x := x + 10' '{x → 100}'
}

@test "custom-6" {
  check 'if true ∧ false then x := 10 else x := 100' '{x → 100}'
}

@test "custom-7" {
  check 'if true ∨ false then x := 10 else x := 100' '{x → 10}'
}

@test "custom-8" {
  check 'if ( x = 0 ∧ true ) then x := 1 else x := 3' '{x → 1}'
}

@test "custom-9" {
  check 'if ( x = 0 ∨ false ) then x := 1 else x := 3' '{x → 1}'
}

