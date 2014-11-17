.file "test_primer_entrega"
.comm A ,80,32
.text
.globl expresiones
expresiones:
enter $32,$0
movl %edi , -4(%rbp)
movl $4, -8(%rbp)
movl $5, -12(%rbp)
movl -8(%rbp), %eax
imull -12(%rbp), %eax
movl %eax, -28(%rbp)
movl -28(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -20(%rbp), %eax
addl -4(%rbp), %eax
movl %eax, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -4(%rbp)
movss .FloatLiteral_3.14, %xmm3
movss %xmm3 ,-16(%rbp)
movl $10, -24(%rbp)
movl -24(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl breaks
breaks:
enter $32,$0
movl %edi , -4(%rbp)
movl $0, -8(%rbp)
.While_condition_23:
movl -8(%rbp), %eax
movl $10, %edx
cmpl %edx, %eax
jl .L0
movl $0, %eax
jmp .Continue0
.L0:
movl $1, %eax
.Continue0:
movl %eax, -12(%rbp)
cmpl $0, -12(%rbp)
je .End_While_23
movl -8(%rbp), %eax
addl $1, %eax
movl %eax, -16(%rbp)
movl -16(%rbp), %ecx
movl %ecx, -8(%rbp)
movl -4(%rbp), %eax
movl $2, %edx
cmpl %edx, %eax
jl .L1
movl $0, %eax
jmp .Continue1
.L1:
movl $1, %eax
.Continue1:
movl %eax, -20(%rbp)
cmpl $1, -20(%rbp)
je .if_28
jmp .endif_28
.if_28:
jmp .End_While_23
jmp .endElse_29
.endif_28:
jmp .While_condition_23
.endElse_29:
jmp .While_condition_23
.End_While_23:
movl -8(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl break_cont
break_cont:
enter $32,$0
movl %edi , -4(%rbp)
movl $0, -12(%rbp)
.While_condition_45:
movl -12(%rbp), %eax
movl $10, %edx
cmpl %edx, %eax
jl .L2
movl $0, %eax
jmp .Continue2
.L2:
movl $1, %eax
.Continue2:
movl %eax, -16(%rbp)
cmpl $0, -16(%rbp)
je .End_While_45
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -20(%rbp)
movl -20(%rbp), %ecx
movl %ecx, -12(%rbp)
movss .FloatLiteral_2.3, %xmm3
movss %xmm3 ,-8(%rbp)
movl -4(%rbp), %eax
movl $0, %edx
cmpl %edx, %eax
jl .L3
movl $0, %eax
jmp .Continue3
.L3:
movl $1, %eax
.Continue3:
movl %eax, -24(%rbp)
cmpl $1, -24(%rbp)
je .if_52
jmp .endif_52
.if_52:
jmp .End_While_45
jmp .endElse_53
.endif_52:
jmp .While_condition_45
.endElse_53:
jmp .While_condition_45
.End_While_45:
movl -12(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl dados
dados:
enter $240,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl %edx , -12(%rbp)
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L4
movl $0, %eax
jmp .Continue4
.L4:
movl $1, %eax
.Continue4:
movl %eax, -16(%rbp)
movl -16(%rbp), %eax
xorl $1, %eax
movl %eax, -20(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L5
movl $0, %eax
jmp .Continue5
.L5:
movl $1, %eax
.Continue5:
movl %eax, -24(%rbp)
movl -24(%rbp), %eax
xorl $1, %eax
movl %eax, -28(%rbp)
movl -20(%rbp), %edx
movl -28(%rbp), %eax
andl %edx, %eax
movl %eax, -32(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L6
movl $0, %eax
jmp .Continue6
.L6:
movl $1, %eax
.Continue6:
movl %eax, -36(%rbp)
movl -36(%rbp), %eax
xorl $1, %eax
movl %eax, -40(%rbp)
movl -32(%rbp), %edx
movl -40(%rbp), %eax
andl %edx, %eax
movl %eax, -44(%rbp)
cmpl $1, -44(%rbp)
je .if_69
jmp .endif_69
.if_69:
movss .FloatLiteral_1.0, %xmm0
.endif_69:
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L7
movl $0, %eax
jmp .Continue7
.L7:
movl $1, %eax
.Continue7:
movl %eax, -48(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L8
movl $0, %eax
jmp .Continue8
.L8:
movl $1, %eax
.Continue8:
movl %eax, -52(%rbp)
movl -52(%rbp), %eax
xorl $1, %eax
movl %eax, -56(%rbp)
movl -48(%rbp), %edx
movl -56(%rbp), %eax
andl %edx, %eax
movl %eax, -60(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L9
movl $0, %eax
jmp .Continue9
.L9:
movl $1, %eax
.Continue9:
movl %eax, -64(%rbp)
movl -64(%rbp), %eax
xorl $1, %eax
movl %eax, -68(%rbp)
movl -60(%rbp), %edx
movl -68(%rbp), %eax
andl %edx, %eax
movl %eax, -72(%rbp)
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L10
movl $0, %eax
jmp .Continue10
.L10:
movl $1, %eax
.Continue10:
movl %eax, -76(%rbp)
movl -76(%rbp), %eax
xorl $1, %eax
movl %eax, -80(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L11
movl $0, %eax
jmp .Continue11
.L11:
movl $1, %eax
.Continue11:
movl %eax, -84(%rbp)
movl -80(%rbp), %edx
movl -84(%rbp), %eax
andl %edx, %eax
movl %eax, -88(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L12
movl $0, %eax
jmp .Continue12
.L12:
movl $1, %eax
.Continue12:
movl %eax, -92(%rbp)
movl -92(%rbp), %eax
xorl $1, %eax
movl %eax, -96(%rbp)
movl -88(%rbp), %edx
movl -96(%rbp), %eax
andl %edx, %eax
movl %eax, -100(%rbp)
movl -72(%rbp), %edx
movl -100(%rbp), %eax
orl %edx, %eax
movl %eax, -104(%rbp)
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L13
movl $0, %eax
jmp .Continue13
.L13:
movl $1, %eax
.Continue13:
movl %eax, -108(%rbp)
movl -108(%rbp), %eax
xorl $1, %eax
movl %eax, -112(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L14
movl $0, %eax
jmp .Continue14
.L14:
movl $1, %eax
.Continue14:
movl %eax, -116(%rbp)
movl -116(%rbp), %eax
xorl $1, %eax
movl %eax, -120(%rbp)
movl -112(%rbp), %edx
movl -120(%rbp), %eax
andl %edx, %eax
movl %eax, -124(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L15
movl $0, %eax
jmp .Continue15
.L15:
movl $1, %eax
.Continue15:
movl %eax, -128(%rbp)
movl -124(%rbp), %edx
movl -128(%rbp), %eax
andl %edx, %eax
movl %eax, -132(%rbp)
movl -104(%rbp), %edx
movl -132(%rbp), %eax
orl %edx, %eax
movl %eax, -136(%rbp)
cmpl $1, -136(%rbp)
je .if_82
jmp .endif_82
.if_82:
movss .FloatLiteral_4.0, %xmm0
.endif_82:
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L16
movl $0, %eax
jmp .Continue16
.L16:
movl $1, %eax
.Continue16:
movl %eax, -140(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L17
movl $0, %eax
jmp .Continue17
.L17:
movl $1, %eax
.Continue17:
movl %eax, -144(%rbp)
movl -140(%rbp), %edx
movl -144(%rbp), %eax
andl %edx, %eax
movl %eax, -148(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L18
movl $0, %eax
jmp .Continue18
.L18:
movl $1, %eax
.Continue18:
movl %eax, -152(%rbp)
movl -152(%rbp), %eax
xorl $1, %eax
movl %eax, -156(%rbp)
movl -148(%rbp), %edx
movl -156(%rbp), %eax
andl %edx, %eax
movl %eax, -160(%rbp)
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L19
movl $0, %eax
jmp .Continue19
.L19:
movl $1, %eax
.Continue19:
movl %eax, -164(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L20
movl $0, %eax
jmp .Continue20
.L20:
movl $1, %eax
.Continue20:
movl %eax, -168(%rbp)
movl -168(%rbp), %eax
xorl $1, %eax
movl %eax, -172(%rbp)
movl -164(%rbp), %edx
movl -172(%rbp), %eax
andl %edx, %eax
movl %eax, -176(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L21
movl $0, %eax
jmp .Continue21
.L21:
movl $1, %eax
.Continue21:
movl %eax, -180(%rbp)
movl -176(%rbp), %edx
movl -180(%rbp), %eax
andl %edx, %eax
movl %eax, -184(%rbp)
movl -160(%rbp), %edx
movl -184(%rbp), %eax
orl %edx, %eax
movl %eax, -188(%rbp)
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L22
movl $0, %eax
jmp .Continue22
.L22:
movl $1, %eax
.Continue22:
movl %eax, -192(%rbp)
movl -192(%rbp), %eax
xorl $1, %eax
movl %eax, -196(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L23
movl $0, %eax
jmp .Continue23
.L23:
movl $1, %eax
.Continue23:
movl %eax, -200(%rbp)
movl -196(%rbp), %edx
movl -200(%rbp), %eax
andl %edx, %eax
movl %eax, -204(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L24
movl $0, %eax
jmp .Continue24
.L24:
movl $1, %eax
.Continue24:
movl %eax, -208(%rbp)
movl -204(%rbp), %edx
movl -208(%rbp), %eax
andl %edx, %eax
movl %eax, -212(%rbp)
movl -188(%rbp), %edx
movl -212(%rbp), %eax
orl %edx, %eax
movl %eax, -216(%rbp)
cmpl $1, -216(%rbp)
je .if_110
jmp .endif_110
.if_110:
movss .FloatLiteral_8.50, %xmm0
.endif_110:
movl -4(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L25
movl $0, %eax
jmp .Continue25
.L25:
movl $1, %eax
.Continue25:
movl %eax, -220(%rbp)
movl -8(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L26
movl $0, %eax
jmp .Continue26
.L26:
movl $1, %eax
.Continue26:
movl %eax, -224(%rbp)
movl -220(%rbp), %edx
movl -224(%rbp), %eax
andl %edx, %eax
movl %eax, -228(%rbp)
movl -12(%rbp), %edx
movl $6, %eax
cmpl %eax, %edx
je .L27
movl $0, %eax
jmp .Continue27
.L27:
movl $1, %eax
.Continue27:
movl %eax, -232(%rbp)
movl -228(%rbp), %edx
movl -232(%rbp), %eax
andl %edx, %eax
movl %eax, -236(%rbp)
cmpl $1, -236(%rbp)
je .if_135
jmp .endif_135
.if_135:
movss .FloatLiteral_10.00, %xmm0
.endif_135:
mov %rbp, %rsp
leave
ret
.text
.globl neg
neg:
enter $16,$0
movl %edi , -4(%rbp)
movl -4(%rbp), %eax
xorl $1, %eax
movl %eax, -8(%rbp)
movl -8(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl and
and:
enter $16,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl -4(%rbp), %edx
movl -8(%rbp), %eax
andl %edx, %eax
movl %eax, -12(%rbp)
movl -12(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl or
or:
enter $16,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl -4(%rbp), %edx
movl -8(%rbp), %eax
orl %edx, %eax
movl %eax, -12(%rbp)
movl -12(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl sumatoria
sumatoria:
enter $64,$0
movss %xmm0 , -4(%rbp)
movss .FloatLiteral_0.0, %xmm3
movss %xmm3 ,-8(%rbp)
movl -4(%rbp), %ecx
movl %ecx, -12(%rbp)
.While_condition_170:
movl $0,%edx
cvtsi2ss %edx , % xmm3 
movss  %xmm3 , -24(%rbp)
movss -12(%rbp), %xmm0
movss -24(%rbp), %xmm1
ucomiss %xmm1, %xmm0
ja .L28
movl $0, %eax
jmp .Continue28
.L28:
movl $1, %eax
.Continue28:
movl %eax, -20(%rbp)
movl $0,%edx
cvtsi2ss %edx , % xmm3 
movss  %xmm3 , -32(%rbp)
movss -12(%rbp), %xmm0
movss -32(%rbp), %xmm1
ucomiss %xmm1, %xmm0
ja .L29
movl $0, %eax
jmp .Continue29
.L29:
movl $1, %eax
.Continue29:
movl %eax, -28(%rbp)
movl -20(%rbp), %edx
movl -28(%rbp), %eax
orl %edx, %eax
movl %eax, -36(%rbp)
cmpl $0, -36(%rbp)
je .End_While_170
movl $0,%edx
cvtsi2ss %edx , % xmm3 
movss  %xmm3 , -44(%rbp)
movss -12(%rbp), %xmm0
movss -44(%rbp), %xmm1
ucomiss %xmm1, %xmm0
ja .L30
movl $0, %eax
jmp .Continue30
.L30:
movl $1, %eax
.Continue30:
movl %eax, -40(%rbp)
cmpl $1, -40(%rbp)
je .if_177
jmp .endif_177
.if_177:
movss -8(%rbp), %xmm0
addss -12(%rbp), %xmm0
movss %xmm0, -48(%rbp)
movl -48(%rbp), %ecx
movl %ecx, -8(%rbp)
.endif_177:
movss -12(%rbp), %xmm0
subss .FloatLiteral_1.0, %xmm0
movss %xmm0, -52(%rbp)
movl -52(%rbp), %ecx
movl %ecx, -12(%rbp)
jmp .While_condition_170
.End_While_170:
movl -8(%rbp), %ecx
movl %ecx, -16(%rbp)
movss -16(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl pruArreglos
pruArreglos:
enter $32,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl -8(%rbp), %eax
cltq
movl -4(%rbp), %ebx
movl %ebx, A(,%rax,4)
movl $1, -12(%rbp)
.For_Loop_197:
movl -12(%rbp), %eax
movl $10, %edx
cmpl %edx, %eax
jl .L31
movl $0, %eax
jmp .Continue31
.L31:
movl $1, %eax
.Continue31:
movl %eax, -20(%rbp)
cmpl $0, -20(%rbp)
je .End_For_197
movl -12(%rbp), %eax
cltq
movl -12(%rbp), %ebx
movl %ebx, A(,%rax,4)
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -12(%rbp)
jmp .For_Loop_197
.End_For_197:
movl -8(%rbp), %eax
cltq
movl A(,%rax,4), %eax 
movl %eax, -24(%rbp)
movl -24(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl pruArreglos2
pruArreglos2:
enter $32,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl -8(%rbp), %eax
cltq
movl -4(%rbp), %ebx
movl %ebx, A(,%rax,4)
movl $11, %eax
imull $2, %eax
movl %eax, -20(%rbp)
movl $-10, %eax
addl -20(%rbp), %eax
movl %eax, -24(%rbp)
movl $1, -12(%rbp)
.For_Loop_215:
movl -12(%rbp), %eax
movl -24(%rbp), %edx
cmpl %edx, %eax
jl .L32
movl $0, %eax
jmp .Continue32
.L32:
movl $1, %eax
.Continue32:
movl %eax, -28(%rbp)
cmpl $0, -28(%rbp)
je .End_For_215
movl -12(%rbp), %eax
cltq
movl -12(%rbp), %ebx
movl %ebx, A(,%rax,4)
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -12(%rbp)
jmp .For_Loop_215
.End_For_215:
movl -8(%rbp), %eax
cltq
movl A(,%rax,4), %eax 
movl %eax, -32(%rbp)
movl -32(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl cons1
cons1:
enter $0,$0
movl $1, %eax
leave
ret
.text
.globl pruAritmetica
pruAritmetica:
enter $32,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl -4(%rbp), %eax
movl -8(%rbp), %edx
cmpl %edx, %eax
jg .L33
movl $0, %eax
jmp .Continue33
.L33:
movl $1, %eax
.Continue33:
movl %eax, -16(%rbp)
cmpl $1, -16(%rbp)
je .if_233
jmp .endif_233
.if_233:
movl -4(%rbp), %eax
subl -8(%rbp), %eax
movl %eax, -20(%rbp)
movl -20(%rbp), %ecx
movl %ecx, -12(%rbp)
jmp .endElse_234
.endif_233:
movl -4(%rbp), %edx
movl -8(%rbp), %eax
cmpl %eax, %edx
je .L34
movl $0, %eax
jmp .Continue34
.L34:
movl $1, %eax
.Continue34:
movl %eax, -24(%rbp)
cmpl $1, -24(%rbp)
je .if_241
jmp .endif_241
.if_241:
movl -4(%rbp), %eax
imull $5, %eax
movl %eax, -28(%rbp)
movl -28(%rbp), %ecx
movl %ecx, -12(%rbp)
jmp .endElse_242
.endif_241:
movl -4(%rbp), %eax
subl -8(%rbp), %eax
movl %eax, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -12(%rbp)
.endElse_242:
.endElse_234:
movl -12(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl maxcomdiv
maxcomdiv:
enter $32,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl -4(%rbp), %eax
movl -8(%rbp), %edx
cmpl %edx, %eax
jg .L35
movl $0, %eax
jmp .Continue35
.L35:
movl $1, %eax
.Continue35:
movl %eax, -24(%rbp)
cmpl $1, -24(%rbp)
je .if_261
jmp .endif_261
.if_261:
movl -4(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -8(%rbp), %ecx
movl %ecx, -16(%rbp)
jmp .endElse_262
.endif_261:
movl -8(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -4(%rbp), %ecx
movl %ecx, -16(%rbp)
.endElse_262:
movl $1, -12(%rbp)
.While_condition_273:
movl -12(%rbp), %edx
movl $0, %eax
cmpl %eax, %edx
jne .L36
movl $0, %eax
jmp .Continue36
.L36:
movl $1, %eax
.Continue36:
movl %eax, -28(%rbp)
cmpl $0, -28(%rbp)
je .End_While_273
movl -20(%rbp), %eax
movl $0,%edx
idivl -16(%rbp)
movl %edx, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -12(%rbp)
movl -16(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -12(%rbp), %ecx
movl %ecx, -16(%rbp)
jmp .While_condition_273
.End_While_273:
movl -20(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl alo
alo:
enter $16,$0
movl %edi , -4(%rbp)
movl -4(%rbp), %eax
addl $1, %eax
movl %eax, -8(%rbp)
movl -8(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -4(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl alo2
alo2:
enter $16,$0
movl %edi , -4(%rbp)
movl -4(%rbp) , %edi
call alo
movl %eax, -8(%rbp)
movl -8(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -4(%rbp), %eax
addl $1, %eax
movl %eax, -12(%rbp)
movl -12(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -4(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl mod
mod:
enter $16,$0
movl %edi , -4(%rbp)
movl -4(%rbp), %eax
movl $0,%edx
movl $7, %ecx 
idivl %ecx
movl %edx, -8(%rbp)
movl -8(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl pruMultInt
pruMultInt:
enter $32,$0
movl %edi , -4(%rbp)
movl $5, -8(%rbp)
movl $200, -12(%rbp)
movl -8(%rbp), %eax
imull $100, %eax
movl %eax, -16(%rbp)
movl -16(%rbp), %eax
imull -4(%rbp), %eax
movl %eax, -20(%rbp)
movl -20(%rbp), %eax
imull -12(%rbp), %eax
movl %eax, -24(%rbp)
movl -24(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl pruMultFloat
pruMultFloat:
enter $32,$0
movss %xmm0 , -4(%rbp)
movss .FloatLiteral_5.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss .FloatLiteral_200.0, %xmm3
movss %xmm3 ,-12(%rbp)
movss -8(%rbp), %xmm0
mulss .FloatLiteral_100.0, %xmm0
movss %xmm0, -16(%rbp)
movss -16(%rbp), %xmm0
mulss -4(%rbp), %xmm0
movss %xmm0, -20(%rbp)
movss -20(%rbp), %xmm0
mulss -12(%rbp), %xmm0
movss %xmm0, -24(%rbp)
movss -24(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl sum
sum:
enter $16,$0
movss .FloatLiteral_2.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss .FloatLiteral_5.1, %xmm3
movss %xmm3 ,-4(%rbp)
movss -8(%rbp), %xmm0
addss -4(%rbp), %xmm0
movss %xmm0, -12(%rbp)
movss -12(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl rest
rest:
enter $16,$0
movss .FloatLiteral_2.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss .FloatLiteral_5.1, %xmm3
movss %xmm3 ,-4(%rbp)
movss -8(%rbp), %xmm0
subss -4(%rbp), %xmm0
movss %xmm0, -12(%rbp)
movss -12(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl mult
mult:
enter $16,$0
movss .FloatLiteral_2.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss .FloatLiteral_5.1, %xmm3
movss %xmm3 ,-4(%rbp)
movss -8(%rbp), %xmm0
mulss -4(%rbp), %xmm0
movss %xmm0, -12(%rbp)
movss -12(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl div
div:
enter $16,$0
movss .FloatLiteral_2.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss .FloatLiteral_5.1, %xmm3
movss %xmm3 ,-4(%rbp)
movss -8(%rbp), %xmm0
divss -4(%rbp), %xmm0
movss %xmm0, -12(%rbp)
movss -12(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl comparacion
comparacion:
enter $48,$0
movss .FloatLiteral_n2.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss .FloatLiteral_3.0, %xmm3
movss %xmm3 ,-4(%rbp)
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
jb .L37
movl $0, %eax
jmp .Continue37
.L37:
movl $1, %eax
.Continue37:
movl %eax, -12(%rbp)
cmpl $1, -12(%rbp)
je .if_363
jmp .endif_363
.if_363:
movl $.StringLiteral0 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_364
.endif_363:
movl $.StringLiteral1 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_364:
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
jbe .L38
movl $0, %eax
jmp .Continue38
.L38:
movl $1, %eax
.Continue38:
movl %eax, -16(%rbp)
cmpl $1, -16(%rbp)
je .if_378
jmp .endif_378
.if_378:
movl $.StringLiteral2 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_379
.endif_378:
movl $.StringLiteral3 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_379:
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
ja .L39
movl $0, %eax
jmp .Continue39
.L39:
movl $1, %eax
.Continue39:
movl %eax, -20(%rbp)
cmpl $1, -20(%rbp)
je .if_393
jmp .endif_393
.if_393:
movl $.StringLiteral4 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_394
.endif_393:
movl $.StringLiteral5 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_394:
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
jae .L40
movl $0, %eax
jmp .Continue40
.L40:
movl $1, %eax
.Continue40:
movl %eax, -24(%rbp)
cmpl $1, -24(%rbp)
je .if_408
jmp .endif_408
.if_408:
movl $.StringLiteral6 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_409
.endif_408:
movl $.StringLiteral7 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_409:
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
je .L41
movl $0, %eax
jmp .Continue41
.L41:
movl $1, %eax
.Continue41:
movl %eax, -28(%rbp)
cmpl $1, -28(%rbp)
je .if_423
jmp .endif_423
.if_423:
movl $.StringLiteral8 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_424
.endif_423:
movl $.StringLiteral9 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_424:
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
jne .L42
movl $0, %eax
jmp .Continue42
.L42:
movl $1, %eax
.Continue42:
movl %eax, -32(%rbp)
cmpl $1, -32(%rbp)
je .if_438
jmp .endif_438
.if_438:
movl $.StringLiteral10 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_439
.endif_438:
movl $.StringLiteral11 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_439:
movss .FloatLiteral_n3.0, %xmm3
movss %xmm3 ,-8(%rbp)
movss -8(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
je .L43
movl $0, %eax
jmp .Continue43
.L43:
movl $1, %eax
.Continue43:
movl %eax, -36(%rbp)
cmpl $1, -36(%rbp)
je .if_454
jmp .endif_454
.if_454:
movl $.StringLiteral12 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
jmp .endElse_455
.endif_454:
movl $.StringLiteral13 , %edi
movss -8(%rbp),%xmm0
movss -4(%rbp),%xmm1
movl $0, %eax
call print_float2
.endElse_455:
mov %rbp, %rsp
leave
ret
.text
.globl main
main:
enter $240,$0
movl $.StringLiteral14 , %edi
movl $0, %eax
call init_input
movl $.StringLiteral15 , %edi
movl $0, %eax
call print_string
movl $6 , %edi
call expresiones
movl %eax, -76(%rbp)
movl -76(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral16 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral17 , %edi
movl $0, %eax
call print_string
movl $1 , %edi
call breaks
movl %eax, -80(%rbp)
movl -80(%rbp) , %edi
movl $0, %eax
call print_int
movl $3 , %edi
call breaks
movl %eax, -84(%rbp)
movl -84(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral18 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral19 , %edi
movl $0, %eax
call print_string
movl $1 , %edi
call break_cont
movl %eax, -88(%rbp)
movl -88(%rbp) , %edi
movl $0, %eax
call print_int
movl $-1 , %edi
call break_cont
movl %eax, -92(%rbp)
movl -92(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral20 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral21 , %edi
movl $0, %eax
call print_string
movl $1, %ecx
movl %ecx, -12(%rbp)
movl $0, %ecx
movl %ecx, -8(%rbp)
movl -12(%rbp) , %edi
call neg
movl %eax, -96(%rbp)
movl -96(%rbp), %ecx
movl %ecx, -8(%rbp)
movl -12(%rbp) , %edi
movl -8(%rbp) , %esi
call and
movl %eax, -100(%rbp)
movl -8(%rbp) , %edi
call neg
movl %eax, -104(%rbp)
movl -100(%rbp) , %edi
movl -104(%rbp) , %esi
call or
movl %eax, -108(%rbp)
movl -108(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -4(%rbp) , %edi
movl $0, %eax
call print_int
movl -8(%rbp) , %edi
movl $0, %eax
call print_int
movl -12(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral22 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral23 , %edi
movl $0, %eax
call print_string
movl $1 , %edi
movl $6 , %esi
movl $9 , %edx
call dados
movss %xmm0, -112(%rbp)
movss -112(%rbp),%xmm0
movl $0, %eax
call print_float
movl $9 , %edi
movl $9 , %esi
movl $9 , %edx
call dados
movss %xmm0, -116(%rbp)
movss -116(%rbp),%xmm0
movl $0, %eax
call print_float
movl $5 , %edi
movl $6 , %esi
movl $1 , %edx
call dados
movss %xmm0, -120(%rbp)
movss -120(%rbp),%xmm0
movl $0, %eax
call print_float
movl $.StringLiteral24 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral25 , %edi
movl $0, %eax
call print_string
movl $1 , %edi
movl $6 , %esi
movl $9 , %edx
call dados
movss %xmm0, -124(%rbp)
movss -124(%rbp),%xmm0
movl $0, %eax
call print_float
movl $9 , %edi
movl $9 , %esi
movl $9 , %edx
call dados
movss %xmm0, -128(%rbp)
movss -128(%rbp),%xmm0
movl $0, %eax
call print_float
movl $5 , %edi
movl $6 , %esi
movl $1 , %edx
call dados
movss %xmm0, -132(%rbp)
movss -132(%rbp),%xmm0
movl $0, %eax
call print_float
movl $.StringLiteral26 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral27 , %edi
movl $0, %eax
call print_string
movss .FloatLiteral_8.0, %xmm3
movss %xmm3 ,-28(%rbp)
movss -28(%rbp),%xmm0
call sumatoria
movss %xmm0, -136(%rbp)
movl -136(%rbp), %ecx
movl %ecx, -24(%rbp)
movss -24(%rbp),%xmm0
movl $0, %eax
call print_float
movss .FloatLiteral_4.0,%xmm0
call sumatoria
movss %xmm0, -140(%rbp)
movl -140(%rbp), %ecx
movl %ecx, -24(%rbp)
movss -24(%rbp),%xmm0
movl $0, %eax
call print_float
movl $.StringLiteral28 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral29 , %edi
movl $0, %eax
call print_string
movl $4 , %edi
movl $8 , %esi
call pruArreglos
movl %eax, -144(%rbp)
movl -144(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral30 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral31 , %edi
movl $0, %eax
call print_string
movl $4 , %edi
movl $8 , %esi
call pruArreglos2
movl %eax, -148(%rbp)
movl -148(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral32 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral33 , %edi
movl $0, %eax
call print_string
call cons1
movl %eax, -152(%rbp)
call cons1
movl %eax, -156(%rbp)
movl -156(%rbp), %eax
addl $5, %eax
movl %eax, -160(%rbp)
movl -152(%rbp), %ecx
movl %ecx, -32(%rbp)
.For_Loop_621:
movl -32(%rbp), %eax
movl -160(%rbp), %edx
cmpl %edx, %eax
jl .L44
movl $0, %eax
jmp .Continue44
.L44:
movl $1, %eax
.Continue44:
movl %eax, -164(%rbp)
cmpl $0, -164(%rbp)
je .End_For_621
movl -32(%rbp), %eax
cltq
movl -32(%rbp), %ebx
movl %ebx, -56(%rbp,%rax,4)
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -32(%rbp)
jmp .For_Loop_621
.End_For_621:
movl $4, %eax
cltq
movl -56(%rbp,%rax,4), %eax 
movl %eax, -168(%rbp)
movl -168(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral34 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral35 , %edi
movl $0, %eax
call print_string
movl $4 , %edi
movl $8 , %esi
call pruAritmetica
movl %eax, -172(%rbp)
movl -172(%rbp) , %edi
movl $0, %eax
call print_int
movl $4 , %edi
movl $4 , %esi
call pruAritmetica
movl %eax, -176(%rbp)
movl -176(%rbp) , %edi
movl $0, %eax
call print_int
movl $8 , %edi
movl $5 , %esi
call pruAritmetica
movl %eax, -180(%rbp)
movl -180(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral36 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral37 , %edi
movl $0, %eax
call print_string
movl $6 , %edi
movl $9 , %esi
call maxcomdiv
movl %eax, -184(%rbp)
movl -184(%rbp) , %edi
movl $0, %eax
call print_int
movl $12 , %edi
movl $22 , %esi
call maxcomdiv
movl %eax, -188(%rbp)
movl -188(%rbp) , %edi
movl $0, %eax
call print_int
movl $8 , %edi
movl $3 , %esi
call maxcomdiv
movl %eax, -192(%rbp)
movl -192(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral38 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral39 , %edi
movl $0, %eax
call print_string
movl $7, -60(%rbp)
movl -60(%rbp) , %edi
call alo2
movl %eax, -196(%rbp)
movl -196(%rbp), %ecx
movl %ecx, -60(%rbp)
movl $3, %eax
imull -60(%rbp), %eax
movl %eax, -200(%rbp)
movl -200(%rbp) , %edi
call alo2
movl %eax, -204(%rbp)
movl -204(%rbp), %ecx
movl %ecx, -60(%rbp)
movl -60(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral40 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral41 , %edi
movl $0, %eax
call print_string
movl $90 , %edi
call mod
movl %eax, -208(%rbp)
movl -208(%rbp) , %edi
movl $0, %eax
call print_int
movl $.StringLiteral42 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral43 , %edi
movl $0, %eax
call print_string
movl $2 , %edi
call pruMultInt
movl %eax, -212(%rbp)
movl -212(%rbp) , %edi
movl $0, %eax
call print_int
movss .FloatLiteral_2.0,%xmm0
call pruMultFloat
movss %xmm0, -216(%rbp)
movss -216(%rbp),%xmm0
movl $0, %eax
call print_float
movl $.StringLiteral44 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral45 , %edi
movl $0, %eax
call print_string
movl $1, -64(%rbp)
movl -64(%rbp), %edx
movl $1, %eax
cmpl %eax, %edx
je .L45
movl $0, %eax
jmp .Continue45
.L45:
movl $1, %eax
.Continue45:
movl %eax, -220(%rbp)
cmpl $1, -220(%rbp)
je .if_711
jmp .endif_711
.if_711:
movl $.StringLiteral46 , %edi
movl $0, %eax
call print_string
jmp .endElse_712
.endif_711:
movl $.StringLiteral47 , %edi
movl -64(%rbp) , %esi
movl $0, %eax
call print_string
.endElse_712:
movl $.StringLiteral48 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral49 , %edi
movl $0, %eax
call print_string
call sum
movss %xmm0, -224(%rbp)
movss -224(%rbp),%xmm0
movl $0, %eax
call print_float
call rest
movss %xmm0, -228(%rbp)
movss -228(%rbp),%xmm0
movl $0, %eax
call print_float
call div
movss %xmm0, -232(%rbp)
movss -232(%rbp),%xmm0
movl $0, %eax
call print_float
call mult
movss %xmm0, -236(%rbp)
movss -236(%rbp),%xmm0
movl $0, %eax
call print_float
movl $.StringLiteral50 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral51 , %edi
movl $0, %eax
call print_string
call comparacion
movl $.StringLiteral52 , %edi
movl $0, %eax
call print_string
mov %rbp, %rsp
leave
ret
.FloatLiteral_3.14:
 	.float 3.14
.FloatLiteral_2.3:
 	.float 2.3
.FloatLiteral_1.0:
 	.float 1.0
.FloatLiteral_4.0:
 	.float 4.0
.FloatLiteral_8.50:
 	.float 8.5
.FloatLiteral_10.00:
 	.float 10.0
.FloatLiteral_0.0:
 	.float 0.0
.FloatLiteral_5.0:
 	.float 5.0
.FloatLiteral_200.0:
 	.float 200.0
.FloatLiteral_100.0:
 	.float 100.0
.FloatLiteral_2.0:
 	.float 2.0
.FloatLiteral_5.1:
 	.float 5.1
.FloatLiteral_n2.0:
 	.float -2.0
.FloatLiteral_3.0:
 	.float 3.0
.StringLiteral0:
 	.string "%f < %f \n"
.StringLiteral1:
 	.string "%f >= %f \n"
.StringLiteral2:
 	.string "%f <= %f \n"
.StringLiteral3:
 	.string "%f > %f \n"
.StringLiteral4:
 	.string "%f > %f \n"
.StringLiteral5:
 	.string "%f <= %f \n"
.StringLiteral6:
 	.string "%f>= %f \n"
.StringLiteral7:
 	.string "%f < %f \n"
.StringLiteral8:
 	.string "%f = %f \n"
.StringLiteral9:
 	.string "%f != %f \n"
.StringLiteral10:
 	.string "%f != %f \n"
.StringLiteral11:
 	.string "%f = %f \n"
.FloatLiteral_n3.0:
 	.float -3.0
.StringLiteral12:
 	.string "%f = %f \n"
.StringLiteral13:
 	.string "%f != %f \n"
.StringLiteral14:
 	.string "input"
.StringLiteral15:
 	.string "Prueba expresiones----------------------------------"
.StringLiteral16:
 	.string "---------------------------------------------------------"
.StringLiteral17:
 	.string "Prueba breaks----------------------------------"
.StringLiteral18:
 	.string "---------------------------------------------------------"
.StringLiteral19:
 	.string "Prueba breaks2----------------------------------"
.StringLiteral20:
 	.string "---------------------------------------------------------"
.StringLiteral21:
 	.string "Prueba booleanos----------------------------------"
.StringLiteral22:
 	.string "---------------------------------------------------------"
.StringLiteral23:
 	.string "Prueba condiciones----------------------------------"
.StringLiteral24:
 	.string "---------------------------------------------------------"
.StringLiteral25:
 	.string "Prueba condiciones----------------------------------"
.StringLiteral26:
 	.string "---------------------------------------------------------"
.StringLiteral27:
 	.string "Prueba sumatoria float----------------------------------"
.FloatLiteral_8.0:
 	.float 8.0
.StringLiteral28:
 	.string "---------------------------------------------------------"
.StringLiteral29:
 	.string "Prueba Arreglo y for ----------------------------------"
.StringLiteral30:
 	.string "---------------------------------------------------------"
.StringLiteral31:
 	.string "Prueba Arreglo y for1 ----------------------------------"
.StringLiteral32:
 	.string "---------------------------------------------------------"
.StringLiteral33:
 	.string "Prueba Arreglo y for con llamada ----------------------------------"
.StringLiteral34:
 	.string "---------------------------------------------------------"
.StringLiteral35:
 	.string "Prueba if ----------------------------------"
.StringLiteral36:
 	.string "---------------------------------------------------------"
.StringLiteral37:
 	.string "Prueba MCD ----------------------------------"
.StringLiteral38:
 	.string "---------------------------------------------------------"
.StringLiteral39:
 	.string "Prueba metodos ----------------------------------"
.StringLiteral40:
 	.string "---------------------------------------------------------"
.StringLiteral41:
 	.string "Prueba mod ----------------------------------"
.StringLiteral42:
 	.string "---------------------------------------------------------"
.StringLiteral43:
 	.string "Prueba multiplicacion int and float ----------------------------------"
.StringLiteral44:
 	.string "---------------------------------------------------------"
.StringLiteral45:
 	.string "TEST DEL APUNTE ----------------------------------"
.StringLiteral46:
 	.string "y==1"
.StringLiteral47:
 	.string "y==%d\n"
.StringLiteral48:
 	.string "---------------------------------------------------------"
.StringLiteral49:
 	.string "TEST aritemetico float ----------------------------------"
.StringLiteral50:
 	.string "---------------------------------------------------------"
.StringLiteral51:
 	.string "test comparaciones float ----------------------------------"
.StringLiteral52:
 	.string "---------------------------------------------------------"
