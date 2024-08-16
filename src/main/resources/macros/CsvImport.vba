Sub ImportCSVData()
    Dim ws As Worksheet
    Dim targetSheet As Worksheet
    Dim importRange As Range
    Dim replaceData As Boolean
    
    ' Choose CSV file to import
    With Application.FileDialog(msoFileDialogFilePicker)
        .Filters.Clear
        .Filters.Add "CSV Files", "*.csv"
        .AllowMultiSelect = False
        
        If .Show = -1 Then
            Workbooks.OpenText .SelectedItems(1), DataType:=xlDelimited, TextQualifier:=xlDoubleQuote
            Set importRange = ActiveSheet.UsedRange
        Else
            Exit Sub
        End If
    End With
    
    ' Choose to replace or append data
    replaceData = MsgBox("Do you want to replace existing data?", vbYesNo) = vbYes
    
    ' Choose or create target sheet for import
    On Error Resume Next
    Set targetSheet = ThisWorkbook.Sheets("Imported Data")
    On Error GoTo 0
    
    If targetSheet Is Nothing Then
        Set targetSheet = ThisWorkbook.Sheets.Add(After:=ThisWorkbook.Sheets(ThisWorkbook.Sheets.Count))
        targetSheet.Name = "Imported Data"
    Else
        If replaceData Then
            targetSheet.Cells.Clear
        End If
    End If
    
    ' Copy data to target sheet
    importRange.Copy Destination:=targetSheet.Range("A1")
    
    ' Clean up
    Application.DisplayAlerts = False
    importRange.Parent.Close False
    Application.DisplayAlerts = True
    
End Sub
'working one
Sub Button1_Click()
    Dim wsheet As Worksheet
    Dim file_mrf As String
    Dim lastRow As Long
    
    ' Set the worksheet where you want to append the data
    Set wsheet = ThisWorkbook.Sheets("Sheet1")
    
    ' Prompt user to select the CSV file
    file_mrf = Application.GetOpenFilename("Text Files (*.csv),*.csv", , "Select CSV File with Pipe Delimiter")
    
    ' Find the last row in the worksheet
    lastRow = wsheet.Cells(wsheet.Rows.Count, 1).End(xlUp).Row + 1

    
    ' Import data from CSV file with pipe delimiter, text format, and double quotes qualifiers
    With wsheet.QueryTables.Add(Connection:="TEXT;" & file_mrf, Destination:=wsheet.Cells(lastRow, 1))
        .TextFileParseType = xlDelimited
        .TextFileOtherDelimiter = ","
        .TextFileConsecutiveDelimiter = False
        .TextFileColumnDataTypes = Array(2) ' Set all columns as text format
        .TextFilePlatform = xlWindows
        .Refresh
    End With
End Sub
' msgbox
Sub Button1_Click12()
    Dim wsheet As Worksheet
    Dim file_mrf As String
    Dim lastRow As Long
    Dim userResponse As VbMsgBoxResult
    
    ' Set the worksheet where you want to append the data
    Set wsheet = ThisWorkbook.Sheets("Sheet1")
    
    ' Prompt user to select the CSV file
    file_mrf = Application.GetOpenFilename("Text Files (*.csv),*.csv", , "Select CSV File with Pipe Delimiter")
    
    ' Check if the user selected a file
    If file_mrf = "False" Then
        MsgBox "No file selected. Operation canceled.", vbExclamation
        Exit Sub
    End If
    
    ' Ask user for confirmation before importing data
    userResponse = MsgBox("Do you want to import data from the selected CSV file?", vbYesNoCancel)
    
    If userResponse = vbYes Then
        ' Find the last row in the worksheet
        lastRow = wsheet.Cells(wsheet.Rows.Count, 1).End(xlUp).Row + 1
        
        ' Import data from CSV file with pipe delimiter, text format, and double quotes qualifiers
        With wsheet.QueryTables.Add(Connection:="TEXT;" & file_mrf, Destination:=wsheet.Cells(lastRow, 1))
            .TextFileParseType = xlDelimited
            .TextFileOtherDelimiter = ","
            .TextFileConsecutiveDelimiter = False
            .TextFileColumnDataTypes = Array(2) ' Set all columns as text format
            .TextFilePlatform = xlWindows
            .Refresh
        End With
        
        MsgBox "Data imported successfully.", vbInformation
    ElseIf userResponse = vbNo Then
        MsgBox "Operation canceled by user.", vbInformation
    Else ' User clicked Cancel or closed the dialog box
        MsgBox "Operation canceled.", vbInformation
    End If

End Sub
