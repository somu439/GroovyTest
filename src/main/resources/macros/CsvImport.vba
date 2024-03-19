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

Sub ImportCsvSingleQuote()
    Dim wsheet As Worksheet
    Dim file_mrf As String
    Dim lastRow As Long
    
    ' Set the worksheet where you want to append the data
    Set wsheet = ThisWorkbook.Sheets("Sheet1")
    
    ' Prompt user to select the CSV file
    file_mrf = Application.GetOpenFilename("Text Files (*.csv),*.csv", , "Select CSV File with Pipe Delimiter")
    
    ' Find the last row in the worksheet
    lastRow = wsheet.Cells(wsheet.Rows.Count, 1).End(xlUp).Row + 1
    
    ' Import data from CSV file with comma delimiter and append it to the last row
    With wsheet.QueryTables.Add(Connection:="TEXT;" & file_mrf, Destination:=wsheet.Cells(lastRow, 1))
        .TextFileParseType = xlDelimited
        .TextFileOtherDelimiter = ","
        .TextFileConsecutiveDelimiter = False
        
        ' Preserve original number and date formats
        .TextFileColumnDataTypes = Array(1) ' Set all columns as general format
        
        ' Format specific columns as text if needed (e.g., column 2)
        wsheet.Columns(2).NumberFormat = "@"
        .TextFileTextQualifier = xlTextQualifierSingleQuote
        .TextFilePlatform = xlWindows
        .Refresh
    End With
End Sub
