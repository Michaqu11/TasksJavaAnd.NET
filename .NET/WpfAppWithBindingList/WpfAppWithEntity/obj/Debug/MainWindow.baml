   M S B A M L   `   `   `  ����K  GWpfAppWithEntity, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null3clr-namespace:WpfAppWithEntityWpfAppWithEntity  5   	   !    WpfAppWithEntity.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   0localclr-namespace:WpfAppWithEntity     5   	     � Title$  
MainWindow��5   	   $��363.8��6   $��416.6��6+   .��5	      � $��0,0,18.8,-0.2q�6    � ColumnDefinitions 5
   
   �� 5      $
��211* �6   �� 5      $
��138* �6   .+�5   
   &   System.Windows.Controls.DataGrid  -   5        $��dataGridView1��5      $
��Left=�6(   $	��289��6C   $��	72,35,0,0q�6P   $	��Top=�5      $	��260��6+      CanUserResizeColumns .     AutoGenerateColumns .     CanUserAddRows .    CanUserReorderColumns .     CanUserSortColumns .     RowBackground
 ��ح�$��2��5   H      Columns 5      4  .System.Windows.Controls.DataGridTemplateColumn  5      ,  &System.Windows.Controls.DataGridColumn	   Header$	 Delete��6)   
   CellTemplate
 5      �� 5      .R�5      �� -   5        $��Delete��5   =   5      5      0  *System.Windows.Controls.DataGridTextColumn  5      $	 Model��6%   1  +System.Windows.Controls.DataGridBoundColumn   Binding 64   ��  �� Path$ model�  5      $	 Motor��6)   
 5      �� 5      .R�5      �� ��6(   �� $ motor�5         CellEditingTemplate 5!      �� 5"      .R�5#      �� .+�5$   "   �� ��6*   �� $ motor.model��� 5%   "   ��6*   �� $ motor.displacement��� 5&   "   ��6*   �� $ motor.horsePower�5'      5(      5*        5+      $
	 Year��6%    63   �� $
 year� � 	Resources 5-      =  7System.Windows.Controls.Primitives.DataGridColumnHeader �� 
TargetType��%E   '        �� 5.      "  6   .�5/      -   5        50      51      52      c� 53   
   $
��Left=�6   $��30��6-   $	��Top=�69   $	��410��6Q   $��2��6]   $��0,0,-18.8,0q�6q   .@�54      �� $��25��6   $��0q�6    $	��Top=�6+   $��66��6C   $��Search for:��6N   �� 55      -   5        $��searchTextBox��55   4   $��	0,2,0,1.6q�6   $��45��6)   6K   �� 56      $��
Search in:��6   $��25��6)   $	��Top=�65   �� 57      -   5        $��comboBox��57   Q   $��25��6   $��0q�6#   $	��Top=�6.   $��66��6F   6Q   �� 58      -   5        $
��Find��58      $��25��6$   $	��Top=�60   $��45��6H   6S   5:      5<      5=      