# Cxi-jlox

My naive implementation of Lox from the book 'Crafting Interpreters' by
Robert Nystrom.

I am also making a parallel Guile Implementation. Check it out.

peek token
  if operand? put on stack
  if operator create list with all operands on stack last item is rest of program/expression
  if statement_ender? push everything unto another bigger list and continue parsing
