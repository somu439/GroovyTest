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
