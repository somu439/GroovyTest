Public Sub DataPrep()
Dim lastColumn As Long
lastColumn = Sheet1.Cells(1, Columns.Count).End(xlToLeft).Column
MsgBox lastColumn

Dim out As String
Dim acct As String

lro = ThisWorkbook.Sheets("UserInp").Cells(Rows.Count, 1).End(xlUp).Row
lr = ThisWorkbook.Sheets("UserInp").Cells(Rows.Count, 1).End(xlUp).End(xlUp).Row - 2
'MsgBox lr, vbOKCancel

For col = 3 To lastColumn
For x = 2 To lr
If IsEmpty(Cells(x, 2).Value) And Not IsEmpty(Cells(x, 3).Value) Then
      out = out + acct + "." + Cells(x, 1).Value + "=" + Cells(x, 3).Value + ";"
    'MsgBox out, vbOKCancel
ElseIf Not IsEmpty(Cells(x, 3).Value) Then
    acct = Cells(x, 2).Value
    out = out + acct + "." + Cells(x, 1).Value + "=" + Cells(x, 3).Value + ";"
End If
Next x

ThisWorkbook.Sheets("TestData").Cells(col - 1, 1) = ThisWorkbook.Sheets("UserInp").Cells(1, col)
ThisWorkbook.Sheets("TestData").Cells(col - 1, 3) = out
'MsgBox out, vbOKCancel

' capture output
out = ""
acct = ""

For x = lr + 1 To lro
If IsEmpty(Cells(x, 2).Value) And Not IsEmpty(Cells(x, 3).Value) Then
      out = out + acct + "." + Cells(x, 1).Value + "=" + Cells(x, 3).Value + ";"
    'MsgBox out, vbOKCancel
ElseIf Not IsEmpty(Cells(x, 3).Value) Then
    acct = Cells(x, 2).Value
    out = out + acct + "." + Cells(x, 1).Value + "=" + Cells(x, 3).Value + ";"
End If
Next x

'ThisWorkbook.Sheets("Base").Cells(1, 1) = ThisWorkbook.Sheets("Userdata").Cells(1, 3)
ThisWorkbook.Sheets("TestData").Cells(col - 1, 4) = out
'MsgBox out, vbOKCancel
Next col
MsgBox "Done", vbOKCancel

End Sub
