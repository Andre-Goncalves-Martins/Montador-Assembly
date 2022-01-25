main: addi $s1,$s2,12235    #arroz
add $t1,$t2,$zero 
or $t0,$zero,$t0 
sub $t2,$v0,$t0
sll $a0,$a1,2
beq $t0,$t1,teste
leon:ori $s0,$s1,10
teste:
lw $t5,0($gp)
sw $s6,44($sp)
xori $s2,$t1,0
jal main
jr $ra
j exit

exit: