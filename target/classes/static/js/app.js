// 自定义JavaScript

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 初始化工具提示
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // 初始化弹出框
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
    
    // 自动隐藏警告框
    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert-dismissible');
        alerts.forEach(function(alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);
});

// 确认删除函数
function confirmDelete(message, callback) {
    if (confirm(message || '确定要删除吗？此操作不可撤销。')) {
        if (typeof callback === 'function') {
            callback();
        }
        return true;
    }
    return false;
}

// 显示加载状态
function showLoading(element) {
    if (element) {
        element.innerHTML = '<span class="loading"></span> 处理中...';
        element.disabled = true;
    }
}

// 隐藏加载状态
function hideLoading(element, originalText) {
    if (element) {
        element.innerHTML = originalText || '提交';
        element.disabled = false;
    }
}

// 表单提交处理
function handleFormSubmit(form, callback) {
    form.addEventListener('submit', function(e) {
        var submitBtn = form.querySelector('button[type="submit"]');
        var originalText = submitBtn.innerHTML;
        
        showLoading(submitBtn);
        
        // 如果有回调函数，执行回调
        if (typeof callback === 'function') {
            callback();
        }
        
        // 如果表单验证失败，恢复按钮状态
        setTimeout(function() {
            if (!form.checkValidity()) {
                hideLoading(submitBtn, originalText);
            }
        }, 100);
    });
}

// 表格行点击高亮
function highlightTableRow() {
    var tableRows = document.querySelectorAll('table tbody tr');
    tableRows.forEach(function(row) {
        row.addEventListener('click', function() {
            // 移除其他行的高亮
            tableRows.forEach(function(r) {
                r.classList.remove('table-active');
            });
            // 添加当前行的高亮
            this.classList.add('table-active');
        });
    });
}

// 搜索框实时搜索
function initSearchBox(inputSelector, tableSelector) {
    var searchInput = document.querySelector(inputSelector);
    var table = document.querySelector(tableSelector);
    
    if (searchInput && table) {
        searchInput.addEventListener('input', function() {
            var searchTerm = this.value.toLowerCase();
            var rows = table.querySelectorAll('tbody tr');
            
            rows.forEach(function(row) {
                var text = row.textContent.toLowerCase();
                if (text.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }
}

// 数据表格排序
function sortTable(table, column, direction) {
    var tbody = table.querySelector('tbody');
    var rows = Array.from(tbody.querySelectorAll('tr'));
    
    rows.sort(function(a, b) {
        var aVal = a.cells[column].textContent.trim();
        var bVal = b.cells[column].textContent.trim();
        
        // 尝试转换为数字
        var aNum = parseFloat(aVal);
        var bNum = parseFloat(bVal);
        
        if (!isNaN(aNum) && !isNaN(bNum)) {
            return direction === 'asc' ? aNum - bNum : bNum - aNum;
        } else {
            return direction === 'asc' ? aVal.localeCompare(bVal) : bVal.localeCompare(aVal);
        }
    });
    
    // 重新插入排序后的行
    rows.forEach(function(row) {
        tbody.appendChild(row);
    });
}

// 初始化表格排序
function initTableSorting() {
    var tables = document.querySelectorAll('table[data-sortable="true"]');
    tables.forEach(function(table) {
        var headers = table.querySelectorAll('th[data-sortable="true"]');
        headers.forEach(function(header, index) {
            header.style.cursor = 'pointer';
            header.addEventListener('click', function() {
                var direction = this.dataset.direction || 'asc';
                sortTable(table, index, direction);
                this.dataset.direction = direction === 'asc' ? 'desc' : 'asc';
            });
        });
    });
}

// 导出表格数据为CSV
function exportTableToCSV(tableId, filename) {
    var table = document.getElementById(tableId);
    if (!table) return;
    
    var csv = [];
    var rows = table.querySelectorAll('tr');
    
    rows.forEach(function(row) {
        var rowData = [];
        var cells = row.querySelectorAll('td, th');
        cells.forEach(function(cell) {
            rowData.push('"' + cell.textContent.replace(/"/g, '""') + '"');
        });
        csv.push(rowData.join(','));
    });
    
    var csvContent = csv.join('\n');
    var blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    var link = document.createElement('a');
    
    if (link.download !== undefined) {
        var url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', filename || 'export.csv');
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
}

// 打印表格
function printTable(tableId) {
    var table = document.getElementById(tableId);
    if (!table) return;
    
    var printWindow = window.open('', '_blank');
    printWindow.document.write('<html><head><title>打印</title>');
    printWindow.document.write('<style>');
    printWindow.document.write('body { font-family: Arial, sans-serif; }');
    printWindow.document.write('table { border-collapse: collapse; width: 100%; }');
    printWindow.document.write('th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }');
    printWindow.document.write('th { background-color: #f2f2f2; }');
    printWindow.document.write('</style>');
    printWindow.document.write('</head><body>');
    printWindow.document.write(table.outerHTML);
    printWindow.document.write('</body></html>');
    printWindow.document.close();
    printWindow.print();
}

// 验证表单
function validateForm(form) {
    var isValid = true;
    var requiredFields = form.querySelectorAll('[required]');
    
    requiredFields.forEach(function(field) {
        if (!field.value.trim()) {
            field.classList.add('is-invalid');
            isValid = false;
        } else {
            field.classList.remove('is-invalid');
        }
    });
    
    return isValid;
}

// 重置表单
function resetForm(form) {
    form.reset();
    var invalidFields = form.querySelectorAll('.is-invalid');
    invalidFields.forEach(function(field) {
        field.classList.remove('is-invalid');
    });
}

// 显示消息
function showMessage(message, type) {
    type = type || 'info';
    var alertClass = 'alert-' + type;
    var iconClass = type === 'success' ? 'fa-check-circle' : 
                   type === 'error' ? 'fa-exclamation-circle' : 
                   type === 'warning' ? 'fa-exclamation-triangle' : 'fa-info-circle';
    
    var alertHtml = '<div class="alert ' + alertClass + ' alert-dismissible fade show" role="alert">' +
                   '<i class="fas ' + iconClass + ' me-2"></i>' +
                   message +
                   '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>' +
                   '</div>';
    
    var container = document.querySelector('.container-fluid, .container');
    if (container) {
        container.insertAdjacentHTML('afterbegin', alertHtml);
        
        // 自动隐藏消息
        setTimeout(function() {
            var alert = container.querySelector('.alert');
            if (alert) {
                var bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            }
        }, 5000);
    }
}

// 工具函数
var Utils = {
    // 格式化日期
    formatDate: function(date, format) {
        if (!date) return '';
        var d = new Date(date);
        var year = d.getFullYear();
        var month = String(d.getMonth() + 1).padStart(2, '0');
        var day = String(d.getDate()).padStart(2, '0');
        
        if (format === 'yyyy-MM-dd') {
            return year + '-' + month + '-' + day;
        } else if (format === 'MM/dd/yyyy') {
            return month + '/' + day + '/' + year;
        } else {
            return year + '年' + month + '月' + day + '日';
        }
    },
    
    // 格式化数字
    formatNumber: function(num, decimals) {
        if (isNaN(num)) return '0';
        return parseFloat(num).toFixed(decimals || 2);
    },
    
    // 防抖函数
    debounce: function(func, wait) {
        var timeout;
        return function executedFunction() {
            var later = function() {
                clearTimeout(timeout);
                func();
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },
    
    // 节流函数
    throttle: function(func, limit) {
        var inThrottle;
        return function() {
            var args = arguments;
            var context = this;
            if (!inThrottle) {
                func.apply(context, args);
                inThrottle = true;
                setTimeout(function() {
                    inThrottle = false;
                }, limit);
            }
        };
    }
};

// 导出全局函数
window.confirmDelete = confirmDelete;
window.showLoading = showLoading;
window.hideLoading = hideLoading;
window.handleFormSubmit = handleFormSubmit;
window.highlightTableRow = highlightTableRow;
window.initSearchBox = initSearchBox;
window.initTableSorting = initTableSorting;
window.exportTableToCSV = exportTableToCSV;
window.printTable = printTable;
window.validateForm = validateForm;
window.resetForm = resetForm;
window.showMessage = showMessage;
window.Utils = Utils;
