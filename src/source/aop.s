.file "testing"
.text
.globl factorial
factorial:
enter $32,$0
movl %edi , -4(%rbp)
movl $15, -16(%rbp)
movl -4(%rbp), %eax
movl -16(%rbp), %edx
cmpl %edx, %eax
jg .L0
movl $0, %eax
jmp .Continue0
.L0:
movl $1, %eax
.Continue0:
movl %eax, -20(%rbp)
cmpl $1, -20(%rbp)
je .if_6
jmp .endif_6
.if_6:
movl $-1, %eax
.endif_6:
movl $0, -12(%rbp)
movl $1, -8(%rbp)
.While_condition_19:
movl -12(%rbp), %eax
movl -4(%rbp), %edx
cmpl %edx, %eax
jl .L1
movl $0, %eax
jmp .Continue1
.L1:
movl $1, %eax
.Continue1:
movl %eax, -24(%rbp)
cmpl $0, -24(%rbp)
je .End_While_19
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -28(%rbp)
movl -28(%rbp), %ecx
movl %ecx, -12(%rbp)
movl -8(%rbp), %eax
imull -12(%rbp), %eax
movl %eax, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -8(%rbp)
jmp .While_condition_19
.End_While_19:
movl -8(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl factorialFor
factorialFor:
enter $32,$0
movl %edi , -4(%rbp)
movl $15, -20(%rbp)
movl -4(%rbp), %eax
movl -20(%rbp), %edx
cmpl %edx, %eax
jg .L2
movl $0, %eax
jmp .Continue2
.L2:
movl $1, %eax
.Continue2:
movl %eax, -24(%rbp)
cmpl $1, -24(%rbp)
je .if_39
jmp .endif_39
.if_39:
movl $-1, %eax
.endif_39:
movl $1, -16(%rbp)
movl $1, -12(%rbp)
movl $1, -8(%rbp)
.For_Loop_52:
movl -8(%rbp), %eax
movl -4(%rbp), %edx
cmpl %edx, %eax
jl .L3
movl $0, %eax
jmp .Continue3
.L3:
movl $1, %eax
.Continue3:
movl %eax, -28(%rbp)
cmpl $0, -28(%rbp)
je .End_For_52
movl -12(%rbp), %eax
imull -8(%rbp), %eax
movl %eax, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -12(%rbp)
movl -8(%rbp), %eax
addl $1, %eax
movl %eax, -8(%rbp)
jmp .For_Loop_52
.End_For_52:
movl -12(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl factorialF
factorialF:
enter $32,$0
movss %xmm0 , -4(%rbp)
movss .FloatLiteral_15.0, %xmm3
movss %xmm3 ,-16(%rbp)
movss -4(%rbp), %xmm0
movss -16(%rbp), %xmm1
ucomiss %xmm1, %xmm0
ja .L4
movl $0, %eax
jmp .Continue4
.L4:
movl $1, %eax
.Continue4:
movl %eax, -20(%rbp)
cmpl $1, -20(%rbp)
je .if_73
jmp .endif_73
.if_73:
movss .FloatLiteral_n1.0, %xmm0
.endif_73:
movss .FloatLiteral_0.0, %xmm3
movss %xmm3 ,-12(%rbp)
movss .FloatLiteral_1.0, %xmm3
movss %xmm3 ,-8(%rbp)
.While_condition_86:
movss -12(%rbp), %xmm0
movss -4(%rbp), %xmm1
ucomiss %xmm1, %xmm0
jb .L5
movl $0, %eax
jmp .Continue5
.L5:
movl $1, %eax
.Continue5:
movl %eax, -24(%rbp)
cmpl $0, -24(%rbp)
je .End_While_86
movss -12(%rbp), %xmm0
addss .FloatLiteral_1.0, %xmm0
movss %xmm0, -28(%rbp)
movl -28(%rbp), %ecx
movl %ecx, -12(%rbp)
movss -8(%rbp), %xmm0
mulss -12(%rbp), %xmm0
movss %xmm0, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -8(%rbp)
jmp .While_condition_86
.End_While_86:
movss -8(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl factorialArray
factorialArray:
enter $128,$0
movl %edi , -4(%rbp)
movl $15, -84(%rbp)
movl $0, -16(%rbp)
.While_condition_110:
movl -16(%rbp), %eax
movl -84(%rbp), %edx
cmpl %edx, %eax
jl .L6
movl $0, %eax
jmp .Continue6
.L6:
movl $1, %eax
.Continue6:
movl %eax, -88(%rbp)
cmpl $0, -88(%rbp)
je .End_While_110
movl $0, -12(%rbp)
movl $1, -8(%rbp)
.While_condition_118:
movl -12(%rbp), %eax
movl -16(%rbp), %edx
cmpl %edx, %eax
jl .L7
movl $0, %eax
jmp .Continue7
.L7:
movl $1, %eax
.Continue7:
movl %eax, -92(%rbp)
cmpl $0, -92(%rbp)
je .End_While_118
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -96(%rbp)
movl -96(%rbp), %ecx
movl %ecx, -12(%rbp)
movl -8(%rbp), %eax
imull -12(%rbp), %eax
movl %eax, -100(%rbp)
movl -100(%rbp), %ecx
movl %ecx, -8(%rbp)
jmp .While_condition_118
.End_While_118:
movl -16(%rbp), %eax
cltq
movl -8(%rbp), %ebx
movl %ebx, -80(%rbp,%rax,4)
movl -16(%rbp), %eax
addl $1, %eax
movl %eax, -104(%rbp)
movl -104(%rbp), %ecx
movl %ecx, -16(%rbp)
jmp .While_condition_110
.End_While_110:
movl -84(%rbp), %eax
subl $1, %eax
movl %eax, -108(%rbp)
movl -4(%rbp), %eax
movl -108(%rbp), %edx
cmpl %edx, %eax
jg .L8
movl $0, %eax
jmp .Continue8
.L8:
movl $1, %eax
.Continue8:
movl %eax, -112(%rbp)
cmpl $1, -112(%rbp)
je .if_135
jmp .endif_135
.if_135:
movl $-1, %eax
jmp .endElse_137
.endif_135:
movl -4(%rbp), %eax
cltq
movl -80(%rbp,%rax,4), %eax 
movl %eax, -116(%rbp)
movl -116(%rbp), %eax
.endElse_137:
mov %rbp, %rsp
leave
ret
.text
.globl nthprime
nthprime:
enter $64,$0
movl %edi , -4(%rbp)
movl $0, -12(%rbp)
movl $2, -16(%rbp)
movl -4(%rbp), %eax
addl $1, %eax
movl %eax, -20(%rbp)
movl -20(%rbp), %ecx
movl %ecx, -4(%rbp)
.While_condition_162:
movl -4(%rbp), %eax
movl $0, %edx
cmpl %edx, %eax
jg .L9
movl $0, %eax
jmp .Continue9
.L9:
movl $1, %eax
.Continue9:
movl %eax, -24(%rbp)
cmpl $0, -24(%rbp)
je .End_While_162
movl $0, %ecx
movl %ecx, -8(%rbp)
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -28(%rbp)
movl -28(%rbp), %ecx
movl %ecx, -12(%rbp)
.While_condition_170:
movl -8(%rbp), %eax
xorl $1, %eax
movl %eax, -32(%rbp)
movl -16(%rbp), %eax
movl -12(%rbp), %edx
cmpl %edx, %eax
jl .L10
movl $0, %eax
jmp .Continue10
.L10:
movl $1, %eax
.Continue10:
movl %eax, -36(%rbp)
movl -32(%rbp), %edx
movl -36(%rbp), %eax
andl %edx, %eax
movl %eax, -40(%rbp)
cmpl $0, -40(%rbp)
je .End_While_170
movl -12(%rbp), %eax
movl $0,%edx
idivl -16(%rbp)
movl %edx, -44(%rbp)
movl -44(%rbp), %edx
movl $0, %eax
cmpl %eax, %edx
je .L11
movl $0, %eax
jmp .Continue11
.L11:
movl $1, %eax
.Continue11:
movl %eax, -48(%rbp)
cmpl $1, -48(%rbp)
je .if_176
jmp .endif_176
.if_176:
movl $1, %ecx
movl %ecx, -8(%rbp)
jmp .endElse_178
.endif_176:
movl -16(%rbp), %eax
addl $1, %eax
movl %eax, -52(%rbp)
movl -52(%rbp), %ecx
movl %ecx, -16(%rbp)
.endElse_178:
jmp .While_condition_170
.End_While_170:
movl $2, -16(%rbp)
movl -8(%rbp), %eax
xorl $1, %eax
movl %eax, -56(%rbp)
cmpl $1, -56(%rbp)
je .if_195
jmp .endif_195
.if_195:
movl -4(%rbp), %eax
subl $1, %eax
movl %eax, -60(%rbp)
movl -60(%rbp), %ecx
movl %ecx, -4(%rbp)
.endif_195:
jmp .While_condition_162
.End_While_162:
movl -12(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl nthprimeArray
nthprimeArray:
enter $432,$0
movl %edi , -4(%rbp)
movl $0, -412(%rbp)
.While_condition_216:
movl -412(%rbp), %eax
movl $100, %edx
cmpl %edx, %eax
jl .L12
movl $0, %eax
jmp .Continue12
.L12:
movl $1, %eax
.Continue12:
movl %eax, -416(%rbp)
cmpl $0, -416(%rbp)
je .End_While_216
movl -412(%rbp) , %edi
call nthprime
movl %eax, -420(%rbp)
movl -412(%rbp), %eax
cltq
movl -420(%rbp), %ebx
movl %ebx, -408(%rbp,%rax,4)
movl -412(%rbp), %eax
addl $1, %eax
movl %eax, -424(%rbp)
movl -424(%rbp), %ecx
movl %ecx, -412(%rbp)
jmp .While_condition_216
.End_While_216:
movl -4(%rbp), %eax
subl $1, %eax
movl %eax, -428(%rbp)
movl -428(%rbp), %eax
cltq
movl -408(%rbp,%rax,4), %eax 
movl %eax, -432(%rbp)
movl -432(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl int2bin
int2bin:
enter $80,$0
movl %edi , -4(%rbp)
movl $0, -24(%rbp)
movl $0, -16(%rbp)
.While_condition_241:
movl -4(%rbp), %eax
movl $0, %edx
cmpl %edx, %eax
jg .L13
movl $0, %eax
jmp .Continue13
.L13:
movl $1, %eax
.Continue13:
movl %eax, -28(%rbp)
cmpl $1, -28(%rbp)
je .if_243
jmp .endif_243
.if_243:
movl -4(%rbp), %eax
movl $0,%edx
movl $2, %ecx 
idivl %ecx
movl %edx, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -20(%rbp)
movl $0, -8(%rbp)
.While_condition_253:
movl -8(%rbp), %eax
movl -16(%rbp), %edx
cmpl %edx, %eax
jl .L14
movl $0, %eax
jmp .Continue14
.L14:
movl $1, %eax
.Continue14:
movl %eax, -36(%rbp)
cmpl $1, -36(%rbp)
je .if_255
jmp .endif_255
.if_255:
movl -20(%rbp), %eax
imull $10, %eax
movl %eax, -40(%rbp)
movl -40(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -8(%rbp), %eax
addl $1, %eax
movl %eax, -44(%rbp)
movl -44(%rbp), %ecx
movl %ecx, -8(%rbp)
jmp .While_condition_253
jmp .endElse_256
.endif_255:
jmp .End_While_253
.endElse_256:
jmp .While_condition_253
.End_While_253:
movl -24(%rbp), %eax
addl -20(%rbp), %eax
movl %eax, -48(%rbp)
movl -48(%rbp), %ecx
movl %ecx, -24(%rbp)
movl -16(%rbp), %eax
addl $1, %eax
movl %eax, -52(%rbp)
movl -52(%rbp), %ecx
movl %ecx, -16(%rbp)
movl -4(%rbp), %eax
movl $0,%edx
movl $2, %ecx 
idivl %ecx
movl %eax, -56(%rbp)
movl -56(%rbp), %ecx
movl %ecx, -4(%rbp)
jmp .While_condition_241
jmp .endElse_244
.endif_243:
jmp .End_While_241
.endElse_244:
movl $0, -12(%rbp)
.While_condition_293:
movl -12(%rbp), %eax
movl -16(%rbp), %edx
cmpl %edx, %eax
jl .L15
movl $0, %eax
jmp .Continue15
.L15:
movl $1, %eax
.Continue15:
movl %eax, -60(%rbp)
cmpl $1, -60(%rbp)
je .if_295
jmp .endif_295
.if_295:
movl -4(%rbp), %eax
imull $10, %eax
movl %eax, -64(%rbp)
movl -64(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -68(%rbp)
movl -68(%rbp), %ecx
movl %ecx, -12(%rbp)
jmp .While_condition_293
jmp .endElse_296
.endif_295:
jmp .End_While_293
.endElse_296:
jmp .While_condition_293
.End_While_293:
jmp .While_condition_241
.End_While_241:
movl -24(%rbp), %eax
addl -4(%rbp), %eax
movl %eax, -72(%rbp)
movl -72(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl gcd
gcd:
enter $48,$0
movl %edi , -4(%rbp)
movl %esi , -8(%rbp)
movl $1, -12(%rbp)
movl -12(%rbp), %ecx
movl %ecx, -16(%rbp)
.While_condition_331:
movl -4(%rbp), %eax
addl -8(%rbp), %eax
movl %eax, -20(%rbp)
movl -12(%rbp), %eax
movl -20(%rbp), %edx
cmpl %edx, %eax
jl .L16
movl $0, %eax
jmp .Continue16
.L16:
movl $1, %eax
.Continue16:
movl %eax, -24(%rbp)
cmpl $0, -24(%rbp)
je .End_While_331
movl -4(%rbp), %eax
movl $0,%edx
idivl -12(%rbp)
movl %edx, -28(%rbp)
movl -28(%rbp), %edx
movl $0, %eax
cmpl %eax, %edx
je .L17
movl $0, %eax
jmp .Continue17
.L17:
movl $1, %eax
.Continue17:
movl %eax, -32(%rbp)
movl -8(%rbp), %eax
movl $0,%edx
idivl -12(%rbp)
movl %edx, -36(%rbp)
movl -36(%rbp), %edx
movl $0, %eax
cmpl %eax, %edx
je .L18
movl $0, %eax
jmp .Continue18
.L18:
movl $1, %eax
.Continue18:
movl %eax, -40(%rbp)
movl -32(%rbp), %edx
movl -40(%rbp), %eax
andl %edx, %eax
movl %eax, -44(%rbp)
cmpl $1, -44(%rbp)
je .if_336
jmp .endif_336
.if_336:
movl -12(%rbp), %ecx
movl %ecx, -16(%rbp)
.endif_336:
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -48(%rbp)
movl -48(%rbp), %ecx
movl %ecx, -12(%rbp)
jmp .While_condition_331
.End_While_331:
movl -16(%rbp), %eax
mov %rbp, %rsp
leave
ret
.text
.globl potenciaR
potenciaR:
enter $48,$0
movss %xmm0 , -4(%rbp)
movl %edi , -8(%rbp)
movss .FloatLiteral_1.0, %xmm3
movss %xmm3 ,-16(%rbp)
movl $1, -12(%rbp)
.While_condition_364:
movl -12(%rbp), %eax
movl -8(%rbp), %edx
cmpl %edx, %eax
jl .L19
movl $0, %eax
jmp .Continue19
.L19:
movl $1, %eax
.Continue19:
movl %eax, -20(%rbp)
movl -12(%rbp), %edx
movl -8(%rbp), %eax
cmpl %eax, %edx
je .L20
movl $0, %eax
jmp .Continue20
.L20:
movl $1, %eax
.Continue20:
movl %eax, -24(%rbp)
movl -20(%rbp), %edx
movl -24(%rbp), %eax
orl %edx, %eax
movl %eax, -28(%rbp)
cmpl $1, -28(%rbp)
je .if_366
jmp .endif_366
.if_366:
movss -16(%rbp), %xmm0
mulss -4(%rbp), %xmm0
movss %xmm0, -32(%rbp)
movl -32(%rbp), %ecx
movl %ecx, -16(%rbp)
movl -12(%rbp), %eax
addl $1, %eax
movl %eax, -36(%rbp)
movl -36(%rbp), %ecx
movl %ecx, -12(%rbp)
jmp .endElse_369
.endif_366:
jmp .End_While_364
.endElse_369:
jmp .While_condition_364
.End_While_364:
movss -16(%rbp), %xmm0
mov %rbp, %rsp
leave
ret
.text
.globl test
test:
enter $64,$0
movss .FloatLiteral_2.0, %xmm3
movss %xmm3 ,-4(%rbp)
movl $3 , %edi
call factorial
movl %eax, -8(%rbp)
movl $4 , %edi
call factorial
movl %eax, -12(%rbp)
movl -8(%rbp) , %edi
movl -12(%rbp) , %esi
call gcd
movl %eax, -16(%rbp)
movl -16(%rbp) , %edi
movl $0, %eax
call print_int
movl $3 , %edi
call factorial
movl %eax, -20(%rbp)
movl $4 , %edi
call factorial
movl %eax, -24(%rbp)
movl -20(%rbp) , %edi
movl -24(%rbp) , %esi
call gcd
movl %eax, -28(%rbp)
movl -28(%rbp) , %edi
call nthprimeArray
movl %eax, -32(%rbp)
movl -32(%rbp) , %edi
movl $0, %eax
call print_int
movl $3 , %edi
call factorial
movl %eax, -36(%rbp)
movl $4 , %edi
call factorial
movl %eax, -40(%rbp)
movl -36(%rbp) , %edi
movl -40(%rbp) , %esi
call gcd
movl %eax, -44(%rbp)
movl -44(%rbp) , %edi
call nthprimeArray
movl %eax, -48(%rbp)
movss -4(%rbp),%xmm0
movl -48(%rbp) , %edi
call potenciaR
movss %xmm0, -52(%rbp)
movl -52(%rbp), %ecx
movl %ecx, -4(%rbp)
movss -4(%rbp),%xmm0
movl $0, %eax
call print_float
mov %rbp, %rsp
leave
ret
.text
.globl test1
test1:
enter $16,$0
movss .FloatLiteral_2.0, %xmm3
movss %xmm3 ,-4(%rbp)
call test
movss -4(%rbp),%xmm0
movl $0, %eax
call print_float
mov %rbp, %rsp
leave
ret
.text
.globl main
main:
enter $192,$0
movl $.StringLiteral0 , %edi
movl $0, %eax
call init_input
movl $.StringLiteral1 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -40(%rbp)
movl -40(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_451:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L21
movl $0, %eax
jmp .Continue21
.L21:
movl $1, %eax
.Continue21:
movl %eax, -44(%rbp)
cmpl $0, -44(%rbp)
je .End_While_451
movl $0, %eax
call get_int
movl %eax, -48(%rbp)
movl -48(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -4(%rbp) , %edi
call factorial
movl %eax, -52(%rbp)
movl -52(%rbp), %ecx
movl %ecx, -4(%rbp)
movl -4(%rbp) , %edi
movl $0, %eax
call print_int
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -56(%rbp)
movl -56(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_451
.End_While_451:
movl $.StringLiteral2 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral3 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -60(%rbp)
movl -60(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_475:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L22
movl $0, %eax
jmp .Continue22
.L22:
movl $1, %eax
.Continue22:
movl %eax, -64(%rbp)
cmpl $0, -64(%rbp)
je .End_While_475
movl $0, %eax
call get_float
movss %xmm0, -68(%rbp)
movl -68(%rbp), %ecx
movl %ecx, -8(%rbp)
movss -8(%rbp),%xmm0
call factorialF
movss %xmm0, -72(%rbp)
movl -72(%rbp), %ecx
movl %ecx, -8(%rbp)
movss -8(%rbp),%xmm0
movl $0, %eax
call print_float
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -76(%rbp)
movl -76(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_475
.End_While_475:
movl $.StringLiteral4 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral5 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -80(%rbp)
movl -80(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_499:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L23
movl $0, %eax
jmp .Continue23
.L23:
movl $1, %eax
.Continue23:
movl %eax, -84(%rbp)
cmpl $0, -84(%rbp)
je .End_While_499
movl $0, %eax
call get_int
movl %eax, -88(%rbp)
movl -88(%rbp), %ecx
movl %ecx, -12(%rbp)
movl -12(%rbp) , %edi
call factorialArray
movl %eax, -92(%rbp)
movl -92(%rbp), %ecx
movl %ecx, -12(%rbp)
movl -12(%rbp) , %edi
movl $0, %eax
call print_int
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -96(%rbp)
movl -96(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_499
.End_While_499:
movl $.StringLiteral6 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral7 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -100(%rbp)
movl -100(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_523:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L24
movl $0, %eax
jmp .Continue24
.L24:
movl $1, %eax
.Continue24:
movl %eax, -104(%rbp)
cmpl $0, -104(%rbp)
je .End_While_523
movl $0, %eax
call get_int
movl %eax, -108(%rbp)
movl -108(%rbp), %ecx
movl %ecx, -16(%rbp)
movl -16(%rbp) , %edi
call nthprime
movl %eax, -112(%rbp)
movl -112(%rbp), %ecx
movl %ecx, -16(%rbp)
movl -16(%rbp) , %edi
movl $0, %eax
call print_int
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -116(%rbp)
movl -116(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_523
.End_While_523:
movl $.StringLiteral8 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral9 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -120(%rbp)
movl -120(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_547:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L25
movl $0, %eax
jmp .Continue25
.L25:
movl $1, %eax
.Continue25:
movl %eax, -124(%rbp)
cmpl $0, -124(%rbp)
je .End_While_547
movl $0, %eax
call get_int
movl %eax, -128(%rbp)
movl -128(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -20(%rbp) , %edi
call nthprimeArray
movl %eax, -132(%rbp)
movl -132(%rbp), %ecx
movl %ecx, -20(%rbp)
movl -20(%rbp) , %edi
movl $0, %eax
call print_int
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -136(%rbp)
movl -136(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_547
.End_While_547:
movl $.StringLiteral10 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral11 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -140(%rbp)
movl -140(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_571:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L26
movl $0, %eax
jmp .Continue26
.L26:
movl $1, %eax
.Continue26:
movl %eax, -144(%rbp)
cmpl $0, -144(%rbp)
je .End_While_571
movl $0, %eax
call get_int
movl %eax, -148(%rbp)
movl -148(%rbp), %ecx
movl %ecx, -24(%rbp)
movl -24(%rbp) , %edi
call int2bin
movl %eax, -152(%rbp)
movl -152(%rbp), %ecx
movl %ecx, -24(%rbp)
movl -24(%rbp) , %edi
movl $0, %eax
call print_int
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -156(%rbp)
movl -156(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_571
.End_While_571:
movl $.StringLiteral12 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral13 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call get_int
movl %eax, -160(%rbp)
movl -160(%rbp), %ecx
movl %ecx, -36(%rbp)
movl $0, -32(%rbp)
.While_condition_595:
movl -32(%rbp), %eax
movl -36(%rbp), %edx
cmpl %edx, %eax
jl .L27
movl $0, %eax
jmp .Continue27
.L27:
movl $1, %eax
.Continue27:
movl %eax, -164(%rbp)
cmpl $0, -164(%rbp)
je .End_While_595
movl $0, %eax
call get_int
movl %eax, -168(%rbp)
movl $0, %eax
call get_int
movl %eax, -172(%rbp)
movl -168(%rbp) , %edi
movl -172(%rbp) , %esi
call gcd
movl %eax, -176(%rbp)
movl -176(%rbp), %ecx
movl %ecx, -28(%rbp)
movl -28(%rbp) , %edi
movl $0, %eax
call print_int
movl -32(%rbp), %eax
addl $1, %eax
movl %eax, -180(%rbp)
movl -180(%rbp), %ecx
movl %ecx, -32(%rbp)
jmp .While_condition_595
.End_While_595:
movl $.StringLiteral14 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral15 , %edi
movl $0, %eax
call print_string
call test
movl $.StringLiteral16 , %edi
movl $0, %eax
call print_string
movl $.StringLiteral17 , %edi
movl $0, %eax
call print_string
call test1
movl $.StringLiteral18 , %edi
movl $0, %eax
call print_string
movl $0, %eax
call close_input
mov %rbp, %rsp
leave
ret
.FloatLiteral_15.0:
 	.float 15.0
.FloatLiteral_n1.0:
 	.float -1.0
.FloatLiteral_0.0:
 	.float 0.0
.FloatLiteral_1.0:
 	.float 1.0
.FloatLiteral_2.0:
 	.float 2.0
.StringLiteral0:
 	.string "input"
.StringLiteral1:
 	.string "Factorial Enteros----------------------------------"
.StringLiteral2:
 	.string "---------------------------------------------------------"
.StringLiteral3:
 	.string "Factorial Reales----------------------------------"
.StringLiteral4:
 	.string "---------------------------------------------------------"
.StringLiteral5:
 	.string "Factorial Array Enteros----------------------------------"
.StringLiteral6:
 	.string "---------------------------------------------------------"
.StringLiteral7:
 	.string "Nthprime Enteros----------------------------------"
.StringLiteral8:
 	.string "---------------------------------------------------------"
.StringLiteral9:
 	.string "Nthprime Array Enteros----------------------------------"
.StringLiteral10:
 	.string "---------------------------------------------------------"
.StringLiteral11:
 	.string "Int2Bin Enteros----------------------------------"
.StringLiteral12:
 	.string "---------------------------------------------------------"
.StringLiteral13:
 	.string "GCD Enteros----------------------------------"
.StringLiteral14:
 	.string "---------------------------------------------------------"
.StringLiteral15:
 	.string "test----------------------------------"
.StringLiteral16:
 	.string "---------------------------------------------------------"
.StringLiteral17:
 	.string "test1----------------------------------"
.StringLiteral18:
 	.string "---------------------------------------------------------"
