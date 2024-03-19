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
    
    ' Remove the first row from the CSV file
    Dim tempFile As String
    tempFile = Environ("TEMP") & "\temp.csv"
    
    Open file_mrf For Input As #1
    Open tempFile For Output As #2
    
    ' Skip the first row
    Line Input #1, dummy
    
    Do Until EOF(1)
        Line Input #1, dataLine
        Print #2, dataLine
    Loop
    
    Close #1
    Close #2
    
    ' Import data from modified CSV file with comma delimiter and append it to the last row, starting from 2nd row
    With wsheet.QueryTables.Add(Connection:="TEXT;" & tempFile, Destination:=wsheet.Cells(lastRow, 1))
        .TextFileParseType = xlDelimited
        .TextFileOtherDelimiter = ","
        .TextFileConsecutiveDelimiter = False
        
        ' Preserve original number and date formats
        .TextFileColumnDataTypes = Array(1) ' Set all columns as general format
        
        ' Format specific columns as text if needed (e.g., column 2)
        wsheet.Columns(2).NumberFormat = "@"
        
        ' Add single quote as text qualifier
       ' .TextFileTextQualifier = xlTextQualifierSingleQuote
        
        .TextFilePlatform = xlWindows
        .Refresh
    End With
    
    ' Delete temporary file
    Kill tempFile
End Sub

Sub Button1_Click()
    Dim wsheet As Worksheet
    Dim file_mrf As String
    Dim lastRow As Long
    Dim tempFile As String
    Dim chunkSize As Long
    Dim i As Long
    
    ' Set the worksheet where you want to append the data
    Set wsheet = ThisWorkbook.Sheets("Sheet1")
    
    ' Prompt user to select the CSV file
    file_mrf = Application.GetOpenFilename("Text Files (*.csv),*.csv", , "Select CSV File with Pipe Delimiter")
    
    ' Find the last row in the worksheet
    lastRow = wsheet.Cells(wsheet.Rows.Count, 1).End(xlUp).Row + 1
    
    ' Define chunk size (e.g., 500 rows per chunk)
    chunkSize = 500
    
    ' Remove the first row from the CSV file and import data in chunks
    tempFile = Environ("TEMP") & "\temp.csv
    
    Open file_mrf For Input As #1
    
    Do While Not EOF(1)
        Open tempFile For Output As #2
        
        ' Skip the first row
        Line Input #1, dummy
        
        For i = 1 To chunkSize
            If Not EOF(1) Then
                Line Input #1, dataLine
                Print #2, dataLine
            End If
        Next i
        
        Close #2
        
        ' Import data from modified CSV chunk with comma delimiter and append it to the last row, starting from 2nd row
        With wsheet.QueryTables.Add(Connection:="TEXT;" & tempFile, Destination:=wsheet.Cells(lastRow, 1))
            .TextFileParseType = xlDelimited
            .TextFileOtherDelimiter = ","
            .TextFileConsecutiveDelimiter = False
            
            ' Preserve original number and date formats
            .TextFileColumnDataTypes = Array(1) ' Set all columns as general format
            
            ' Format specific columns as text if needed (e.g., column 2)
            wsheet.Columns(2).NumberFormat = "@"
            
            ' Add single quote as text qualifier
            .TextFileTextQualifier = xlTextQualifierSingleQuote
            
            .TextFilePlatform = xlWindows
            .Refresh
        End With
        
        lastRow = wsheet.Cells(wsheet.Rows.Count, 1).End(xlUp).Row + 1
    Loop
    
    Close #1
    
    ' Delete temporary file
    Kill tempFile
End Sub
