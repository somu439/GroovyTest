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


Sub SaveAsPipeDelimitedCSV()
    ' Define the file path and name
    Dim filePath As String
    Dim fileName As String
    Dim textFileContent As String
    Dim fileNumber As Integer

    ' Modify the path and name as needed
    filePath = "C:\Path\To\Your\File\"
    fileName = "YourFileName.txt"

    ' Loop through each cell in the active sheet
    For Each row In ActiveSheet.UsedRange.Rows
        For Each cell In row.Cells
            ' Concatenate cell values with pipe delimiter
            textFileContent = textFileContent & cell.Value & "|"
        Next cell
        ' Add a line break after each row
        textFileContent = textFileContent & vbCrLf
    Next row

    ' Remove the trailing pipe in each row
    textFileContent = Replace(textFileContent, "|", vbCrLf)

    ' Create a text file and write the content
    fileNumber = FreeFile
    Open filePath & fileName For Output As fileNumber
    Print #fileNumber, textFileContent
    Close fileNumber

    ' Display a confirmation message (you can modify this part as needed)
    MsgBox "File saved as " & filePath & fileName, vbInformation

End Sub
