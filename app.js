const dictionaryData = [
  { id: crypto.randomUUID(), code: 'CAT_TRAVEL', name: '差旅费', status: '启用' },
  { id: crypto.randomUUID(), code: 'CAT_SALARY', name: '薪资', status: '启用' },
  { id: crypto.randomUUID(), code: 'CAT_MARKET', name: '市场推广', status: '停用' }
];

const billData = [
  {
    id: crypto.randomUUID(),
    title: '三月机票报销',
    amount: 2800,
    date: '2026-03-02',
    category: '差旅费',
    type: '支出'
  },
  {
    id: crypto.randomUUID(),
    title: '客户回款',
    amount: 15000,
    date: '2026-03-05',
    category: '薪资',
    type: '收入'
  },
  {
    id: crypto.randomUUID(),
    title: '广告投放',
    amount: 4200,
    date: '2026-03-06',
    category: '市场推广',
    type: '支出'
  }
];

const tabs = document.querySelectorAll('.tab-btn');
const panels = document.querySelectorAll('.tab-panel');
const dictionaryTbody = document.getElementById('dictionary-tbody');
const billTbody = document.getElementById('bill-tbody');
const billCategorySelect = document.getElementById('bill-category');

function activateTab(targetId) {
  tabs.forEach((btn) => btn.classList.toggle('active', btn.dataset.tab === targetId));
  panels.forEach((panel) => panel.classList.toggle('active', panel.id === targetId));
}

tabs.forEach((btn) => {
  btn.addEventListener('click', () => activateTab(btn.dataset.tab));
});

function renderDictionary() {
  dictionaryTbody.innerHTML = '';
  dictionaryData.forEach((item) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${item.code}</td>
      <td>${item.name}</td>
      <td><span class="tag ${item.status === '启用' ? 'enable' : 'disable'}">${item.status}</span></td>
      <td><button class="action-link" data-delete-dict="${item.id}">删除</button></td>
    `;
    dictionaryTbody.appendChild(tr);
  });

  billCategorySelect.innerHTML = dictionaryData
    .filter((item) => item.status === '启用')
    .map((item) => `<option value="${item.name}">${item.name}</option>`)
    .join('');
}

function renderBills() {
  billTbody.innerHTML = '';
  billData
    .slice()
    .sort((a, b) => new Date(b.date) - new Date(a.date))
    .forEach((bill) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${bill.title}</td>
        <td>¥${bill.amount.toFixed(2)}</td>
        <td>${bill.date}</td>
        <td>${bill.category}</td>
        <td>${bill.type}</td>
        <td><button class="action-link" data-delete-bill="${bill.id}">删除</button></td>
      `;
      billTbody.appendChild(tr);
    });
}

function formatMoney(value) {
  return `¥${value.toFixed(2)}`;
}

function renderDashboard() {
  const income = billData.filter((b) => b.type === '收入').reduce((sum, b) => sum + b.amount, 0);
  const expense = billData.filter((b) => b.type === '支出').reduce((sum, b) => sum + b.amount, 0);

  document.getElementById('kpi-income').textContent = formatMoney(income);
  document.getElementById('kpi-expense').textContent = formatMoney(expense);
  document.getElementById('kpi-net').textContent = formatMoney(income - expense);
  document.getElementById('kpi-count').textContent = String(billData.length);

  const expenseMap = {};
  billData
    .filter((b) => b.type === '支出')
    .forEach((b) => {
      expenseMap[b.category] = (expenseMap[b.category] || 0) + b.amount;
    });

  const max = Math.max(...Object.values(expenseMap), 1);
  const chart = document.getElementById('category-chart');
  chart.innerHTML = '';

  Object.entries(expenseMap).forEach(([name, amount]) => {
    const row = document.createElement('div');
    row.className = 'bar-row';
    const width = (amount / max) * 100;

    row.innerHTML = `
      <span>${name}</span>
      <div class="bar-track"><div class="bar-fill" style="width:${width}%"></div></div>
      <strong>¥${amount.toFixed(0)}</strong>
    `;
    chart.appendChild(row);
  });

  if (!Object.keys(expenseMap).length) {
    chart.innerHTML = '<p>暂无支出数据。</p>';
  }
}

function rerenderAll() {
  renderDictionary();
  renderBills();
  renderDashboard();
}

document.getElementById('dictionary-form').addEventListener('submit', (event) => {
  event.preventDefault();
  const code = document.getElementById('dict-code').value.trim();
  const name = document.getElementById('dict-name').value.trim();
  const status = document.getElementById('dict-status').value;

  if (!code || !name) return;
  dictionaryData.unshift({ id: crypto.randomUUID(), code, name, status });
  event.target.reset();
  rerenderAll();
});

document.getElementById('bill-form').addEventListener('submit', (event) => {
  event.preventDefault();
  const title = document.getElementById('bill-title').value.trim();
  const amount = Number(document.getElementById('bill-amount').value);
  const date = document.getElementById('bill-date').value;
  const category = billCategorySelect.value;
  const type = document.getElementById('bill-type').value;

  if (!title || Number.isNaN(amount) || amount < 0 || !date || !category) return;
  billData.unshift({ id: crypto.randomUUID(), title, amount, date, category, type });
  event.target.reset();
  rerenderAll();
});

document.body.addEventListener('click', (event) => {
  const target = event.target;
  const dictId = target.getAttribute('data-delete-dict');
  const billId = target.getAttribute('data-delete-bill');

  if (dictId) {
    const idx = dictionaryData.findIndex((d) => d.id === dictId);
    if (idx > -1) {
      dictionaryData.splice(idx, 1);
      rerenderAll();
    }
  }

  if (billId) {
    const idx = billData.findIndex((b) => b.id === billId);
    if (idx > -1) {
      billData.splice(idx, 1);
      rerenderAll();
    }
  }
});

rerenderAll();
